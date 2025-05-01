package com.example.gym.screens.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.model.cadastro.CadastroRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.user.UserService
import kotlinx.coroutines.launch

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

    private val _cadastroMensagem = MutableLiveData<String>()
    val cadastroMensagem: LiveData<String> = _cadastroMensagem

    fun onEmailChanged(newEmail: String) {
        _emailLogin.value = newEmail
    }

    fun onNameChanged(newName: String) {
        _nameLogin.value = newName
    }

    fun onPasswordChange(newPassword: String) {
        _passwordLogin.value = newPassword
    }

    fun onPasswordConfirm(newPassword: String) {
        _confirmPassword.value = newPassword
    }

    fun cadastroUser() {
        val email = _emailLogin.value ?: ""
        val name = _nameLogin.value ?: ""
        val senha = _passwordLogin.value ?: ""
        val confirmSenha = _confirmPassword.value ?: ""

        if (senha != confirmSenha) {
            _cadastroMensagem.value = "As senhas não coincidem."
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val cadastroRequest = CadastroRequest(email = email, name = name, password = senha)
                val response = usuarioService.add(cadastroRequest)

                if (response.isSuccessful) {
                    val cadastroResponse = response.body()
                    if (cadastroResponse != null) {
                        _cadastroMensagem.value = "Cadastro realizado com sucesso!"
                        // Aqui você pode emitir evento de navegação ou resetar os campos
                    } else {
                        _cadastroMensagem.value = "Erro inesperado: resposta vazia."
                    }
                } else {
                    val erro = response.errorBody()?.string() ?: "Erro desconhecido"
                    _cadastroMensagem.value = "Falha ao cadastrar: $erro"
                }

            } catch (e: Exception) {
                _cadastroMensagem.value = "Erro na conexão: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}