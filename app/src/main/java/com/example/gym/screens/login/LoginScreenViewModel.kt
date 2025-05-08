package com.example.gym.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.data.PreferencesManager
import com.example.gym.model.login.LoginRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.auth.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel() : ViewModel() {

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
            _navigationAndStatusEvent.tryEmit(NavigationEvent.ShowStatusMessage("Preencha todos os campos."))
            return
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email = email, senha = password)
                val response = authService.login(loginRequest)

                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        // Bloco corrigido
                        viewModelScope.launch {
                            try {
                                PreferencesManager.saveUserToken(loginResponse.token)
                                _navigationAndStatusEvent.emit(
                                    NavigationEvent.NavigateToHome(loginResponse.token)
                                )
                            } catch (e: Exception) {
                                _navigationAndStatusEvent.emit(
                                    NavigationEvent.ShowStatusMessage("Erro ao salvar sessão")
                                )
                            }
                        }
                    } ?: run {
                        _navigationAndStatusEvent.emit(
                            NavigationEvent.ShowStatusMessage("Resposta inválida do servidor")
                        )
                    }
                }
            } catch (e: Exception) {
                // Tratar outros erros
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