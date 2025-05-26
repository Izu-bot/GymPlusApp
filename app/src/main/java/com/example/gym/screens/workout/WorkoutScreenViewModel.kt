package com.example.gym.screens.workout

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.data.PreferencesManager
import com.example.gym.model.planilha.CreateSpreadsheetRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.planilha.SpreadsheetService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

class WorkoutScreenViewModel: ViewModel() {

    private val _navigationAndStatusEvent = MutableSharedFlow<NavigationEvent>()
    val navigationAndStatusEvent: SharedFlow<NavigationEvent> = _navigationAndStatusEvent


    private val _namePlanilha = MutableLiveData("")
    val namePlanilha: LiveData<String> = _namePlanilha

    fun onChangeNamePlanilha(novaPlanilha: String) {
        _namePlanilha.value = novaPlanilha
    }

    private val spreadsheet: SpreadsheetService by lazy {
        RetrofitFactory().spreadsheet()
    }

    fun criarPlanilha() {
        val nome = _namePlanilha.value ?: ""

        if (nome.isBlank()) {
            viewModelScope.launch {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Planilha não pode ter nome vazio"))
            }

            return
        }

        viewModelScope.launch {
            try {
                val token = PreferencesManager.getToken() ?: run {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Sessão expirada. Faça login novamente."))
                    return@launch
                }

                Log.d("TOKEN_DEBUG", "token: $token")
                val fullToken = "Bearer $token"
                Log.d("TOKEN_DEBUG", "full token: $fullToken")
                val spreadsheetRequest = CreateSpreadsheetRequest(name = nome)
                val response = spreadsheet.create(fullToken, spreadsheetRequest)

                if (response.isSuccessful && response.body() != null) {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Planilha criado com sucesso."))
                }
            } catch (e: HttpException) {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage(e.toString()))
            }

        }
    }
}

sealed class NavigationEvent {
    data class ShowStatusMessage(val message: String) : NavigationEvent()
}