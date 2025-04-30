package com.example.gym.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.model.LoginRequest
import com.example.gym.service.AuthService
import com.example.gym.service.RetrofitFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class LoginScreenViewModel : ViewModel() {
    private val _emailLogin = MutableLiveData<String>()
    val emailLoing: LiveData<String> = _emailLogin

    private val _senhaLogin = MutableLiveData<String>()
    val senhaLogin: LiveData<String> = _senhaLogin

    private val _senhaVisivel = MutableLiveData<Boolean>()
    val senhaVisivel: LiveData<Boolean> = _senhaVisivel

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginSuccess = MutableLiveData<String?>()
    val loginSuccess: LiveData<String?> = _loginSuccess

    private val authService: AuthService by lazy {
        RetrofitFactory().authService()
    }

    // Para mostrar mensagens de erro
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    fun onEmailChanged(novoEmail: String) {
        _emailLogin.value = novoEmail
    }

    fun onSenhaChanged(novaSenha: String) {
        _senhaLogin.value = novaSenha
    }

    fun toggleSenhaVisivel() {
        _senhaVisivel.value = _senhaVisivel.value != true
    }

    fun performLogin() {
        val email = _emailLogin.value ?: ""
        val password = _senhaLogin.value ?: ""

        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Email ou senha em branco"
            return
        }

        _isLoading.value = true

        // Lança um coroutime no escopo do viewmodel
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email = email, senha = password)
                val response = authService.login(loginRequest) // chama a função suspend

                if (response.isSuccessful) {
                    val loginReponse = response.body()
                    if (loginReponse != null)
                    {
                        //sucesso
                        val token = loginReponse.token
                        _loginSuccess.value = token
                        Log.i("Login Sucesso", token)
                    } else {
                        _errorMessage.value = "Erro de servidor"
                    }
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Erro ${response.code()}"
                    _errorMessage.value = "Falha ao logar: $errorMsg"
                }
            } catch (e: HttpException) {
                // Erro específico do Retrofit para respostas não-2xx
                _errorMessage.value = "Erro de comunicação: ${e.message()}"
            }
            catch (e: IOException) {
                // Erro de rede (sem conexão, etc.)
                _errorMessage.value = "Erro de rede. Verifique sua conexão."
            } catch (e: Exception) {
                // Outros erros (parsing JSON, etc.)
                _errorMessage.value = "Ocorreu um erro inesperado: ${e.message}"
            } finally {
                // Garante que o estado de carregamento termine
                _isLoading.value = false
            }
        }
    }
}