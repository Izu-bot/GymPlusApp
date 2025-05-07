package com.example.gym.screens.home

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.data.PreferencesManager
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.user.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {


    private val userService: UserService by lazy {
        RetrofitFactory().cadastroUsuario()
    }

    private val _nameUser = MutableStateFlow("")
    val nameUser: StateFlow<String> = _nameUser

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadUserDataAutomatically()
    }

    private fun loadUserDataAutomatically() {
        viewModelScope.launch {
            try {
                // Passo 1: Obter o token
                val token = PreferencesManager.getUserToken().first()

                if (token.isNullOrBlank()) {
                    _error.value = "Usuário não autenticado"
                    return@launch
                }

                // Passo 2: Fazer a requisição
                val response = userService.getId("Bearer $token")

                // Passo 3: Processar resposta
                when {
                    response.isSuccessful -> {
                        response.body()?.let { user ->
                            _nameUser.value = user.name
                        } ?: run {
                            _error.value = "Dados do usuário não encontrados"
                        }
                    }
                    response.code() == 401 -> {
                        _error.value = "Sessão expirada"
                        PreferencesManager.clearUserToken()
                        // Adicione navegação para login aqui
                    }
                    else -> {
                        _error.value = "Erro: ${response.code()}"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Falha na conexão: ${e.localizedMessage}"
            }
        }
    }

    private fun getHora(): Pair<Int, Int> {
        val agora = Calendar.getInstance()

        val hora = agora.get(Calendar.HOUR_OF_DAY)
        val minutos = agora.get(Calendar.MINUTE)

        return Pair(hora, minutos)
    }

    fun welcomeUser(): String {
        val hora = getHora()

        return if (hora.first >= 5 && hora.first <= 11) {
            "Bom dia"
        } else if (hora.first >= 12 && hora.first <= 17) {
            "Boa tarde"
        } else {
            "Boa noite"
        }
    }
}