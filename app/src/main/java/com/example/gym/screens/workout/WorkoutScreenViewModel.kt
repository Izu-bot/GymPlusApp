package com.example.gym.screens.workout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.data.PreferencesManager
import com.example.gym.model.planilha.CreateSpreadsheetRequest
import com.example.gym.model.planilha.SpreadsheetResponse
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

    private val _spreadsheetList = MutableLiveData<List<SpreadsheetResponse>>()
    val spreadsheetList: LiveData<List<SpreadsheetResponse>> = _spreadsheetList

    fun onChangeNamePlanilha(novaPlanilha: String) {
        _namePlanilha.value = novaPlanilha
    }

    fun limparNomePlanilha() {
        _namePlanilha.value = ""
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

    fun viewSpreadsheet() {
        viewModelScope.launch {
            try {
                val token = PreferencesManager.getToken() ?: run {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Token invalido"))
                    return@launch
                }

                val response = spreadsheet.view("Bearer $token")

                if (response.isSuccessful) {
                    response.body()?.let { list ->
                        _spreadsheetList.value = list
                    } ?: run {
                        _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Dados vazios"))
                    }
                } else {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro: ${response.code()}"))
                }
            } catch (e: Exception)
            {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Exceção: ${e.message}"))
            }
        }
    }


}

sealed class NavigationEvent {
    data class ShowStatusMessage(val message: String) : NavigationEvent()
}