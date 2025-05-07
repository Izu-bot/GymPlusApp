package com.example.gym.screens.cadastro

import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.model.cadastro.CadastroRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.user.UserService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class CadastroScreenViewModel : ViewModel() {

    private val _emailLogin = MutableLiveData("")
    val emailLogin: LiveData<String> = _emailLogin

    private val _nameLogin = MutableLiveData("")
    val nameLogin: LiveData<String> = _nameLogin

    private val _passwordLogin = MutableLiveData("")
    val passwordLogin: LiveData<String> = _passwordLogin

    private val _confirmPassword = MutableLiveData("")
    val confirmPassword: LiveData<String> = _confirmPassword

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val usuarioService : UserService by lazy {
        RetrofitFactory().cadastroUsuario()
    }

    private val _emailErroMessage = MutableLiveData<String?>(null)
    val emailErroMessage: LiveData<String?> = _emailErroMessage

    private val _senhaErroMessage = MutableLiveData<String?>(null)
    val senhaErroMessage: LiveData<String?> = _senhaErroMessage

    private val _navigationAndStatusEvent = MutableSharedFlow<NavigationEvent>()
    val navigationAndStatusEvent: SharedFlow<NavigationEvent> = _navigationAndStatusEvent

    fun onEmailChanged(newEmail: String) {
        _emailLogin.value = newEmail
        if (_emailErroMessage.value != null ) { _emailErroMessage.value = null }
    }

    fun onNameChanged(newName: String) {
        _nameLogin.value = newName
    }

    fun onPasswordChange(newPassword: String) {
        _passwordLogin.value = newPassword
        if (_senhaErroMessage.value != null) { _senhaErroMessage.value = null }
    }

    fun onPasswordConfirm(newPassword: String) {
        _confirmPassword.value = newPassword
        if (_senhaErroMessage.value != null) { _senhaErroMessage.value = null }
    }

    private fun validarCampo(email: String, senha: String, confirmSenha: String): Boolean {
        var emailValidado = true
        var senhasValidas = true

        if (!EMAIL_ADDRESS.matcher(email).matches()) {
            _emailErroMessage.value = "Email está errado."
            emailValidado = false
        }
        if (senha != confirmSenha) {
            _senhaErroMessage.value = "As senhas não conferem."
            senhasValidas = false
        } else if (senha.length < 8) { // Exemplo: Adicionar validação de tamanho mínimo
            _senhaErroMessage.value = "A senha deve ter pelo menos 8 caracteres."
            senhasValidas = false
        }

        return emailValidado && senhasValidas
    }

    fun cadastroUser() {
        val email = _emailLogin.value ?: ""
        val name = _nameLogin.value ?: ""
        val senha = _passwordLogin.value ?: ""
        val confirmSenha = _confirmPassword.value ?: ""

        if (email.isBlank() || name.isBlank() || senha.isBlank() || confirmSenha.isBlank()) {
            viewModelScope.launch {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Preencha todos os campos para se cadastrar."))
            }

            return
        }

        if (!validarCampo(email, senha, confirmSenha)) {
            viewModelScope.launch {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Verifique os erros nos campos."))
            }

            return
        }

//        _isLoading.value = true

        viewModelScope.launch {
            try {
                val cadastroRequest = CadastroRequest(email = email, name = name, password = senha)
                val response = usuarioService.add(cadastroRequest)

                if (response.isSuccessful && response.body() != null) {
                        _navigationAndStatusEvent.emit(NavigationEvent.NavigationToLogin(true))
                } else {
                    if (response.code() == 400) {
                        _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Não foi possivel estabelecer conexão com o servidor"))
                    } else {
                        var errorMsg = response.errorBody()?.string() ?: "Erro ${response.code()}"
                        _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Falha ao cadastrar: $errorMsg"))
                    }
                }
            } catch (e: HttpException) {
            _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro de comunicação: Não foi possivel se conectar ao servidor :("))
        } catch (e: IOException) {
            _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro de rede. Verifique sua conexão."))
        } catch (e: Exception) {
            _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro inesperado: Parece que aconteceu um erro inesperado, tente novamente mais tarde."))
        } finally {
            _isLoading.value = false
        }
        }
    }
}

sealed class NavigationEvent {
    data class NavigationToLogin(val statusCode: Boolean) : NavigationEvent()
    data class ShowStatusMessage(val message: String) : NavigationEvent()
}