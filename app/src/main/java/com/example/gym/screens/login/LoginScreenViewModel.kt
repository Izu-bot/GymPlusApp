package com.example.gym.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.model.login.LoginRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.auth.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class LoginScreenViewModel : ViewModel() {
    init {
        Log.d("LoginVM", "ViewModel Criado! HashCode: ${this.hashCode()}")
    }
    private val _emailLogin = MutableLiveData<String>()
    val emailLogin: LiveData<String> = _emailLogin

    private val _senhaLogin = MutableLiveData<String>()
    val senhaLogin: LiveData<String> = _senhaLogin

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val authService: AuthService by lazy {
        RetrofitFactory().authService()
    }

    private val _erroMessage =  MutableLiveData<String?>(null) // Inicia como null sem erro
    val errorMessage: LiveData<String?> = _erroMessage

    private val _navigationAndStatusEvent = MutableSharedFlow<NavigationEvent>() // Exemplo mais robusto
    val navigationAndStatusEvent: SharedFlow<NavigationEvent> = _navigationAndStatusEvent

    // LiveData para erros gerais ou de sucesso (pode manter o Event wrapper)

    fun onEmailChanged(novoEmail: String) {
        _emailLogin.value = novoEmail
        if (_erroMessage.value != null) { _erroMessage.value = null }

    }

    fun onSenhaChanged(novaSenha: String) {
        _senhaLogin.value = novaSenha
        if (_erroMessage.value != null) { _erroMessage.value = null }
    }


    fun performLogin() {
        val email = _emailLogin.value ?: ""
        val password = _senhaLogin.value ?: ""

        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Preencha todos os campos."))
            }

            return
        }

        // Limpa erros anteriores antes de tentar novamente
        _erroMessage.value = null
        _isLoading.value = true


        // Lança um coroutime no escopo do viewmodel
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email = email, senha = password)
                val response = authService.login(loginRequest) // chama a função suspend

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.token

//                  Dispara um evento para NAVEGAR para Home (passando token opcionalmente)
                    _navigationAndStatusEvent.emit(NavigationEvent.NavigateToHome(token))

                    // Pode salvar o token no data storage

                } else {
                    if (response.code() == 401) {
                        _erroMessage.value = "Email ou senha incorretos."
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Erro ${response.code()}"
                        Log.d("Falha no login", errorMsg)
                        _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Falha no login: Parece que não foi possivel se conectar a sua conta, tente novamente mais tarde."))
                    }
                }
            } catch (e: HttpException) {
                Log.d("Erro HTTP", "${e.message}")
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro de comunicação: Não foi possivel se conectar ao servidor :("))
            } catch (e: IOException) {
                Log.d("Error IO", "${e.message}")
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro de rede. Verifique sua conexão."))
            } catch (e: Exception) {
                Log.d("Erro HTTP", "${e.message}")
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro inesperado: Parece que aconteceu um erro inesperado, tente novamente mais tarde."))
            } finally {
                _isLoading.value = false
            }
        }
    }
}

// Classe selada para representar diferentes tipos de eventos de navegação/status
// Isso torna a observação na UI mais organizada.
sealed class NavigationEvent {
    data class NavigateToHome(val token: String?) : NavigationEvent() // Pode incluir dados se necessario
    data class ShowStatusMessage(val message: String) : NavigationEvent()
}