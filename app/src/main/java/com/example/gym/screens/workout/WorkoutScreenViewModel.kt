package com.example.gym.screens.workout

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gym.data.PreferencesManager
import com.example.gym.model.planilha.CreateSpreadsheetRequest
import com.example.gym.model.planilha.SpreadsheetResponse
import com.example.gym.model.treinos.WorkoutRequest
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.planilha.SpreadsheetService
import com.example.gym.service.workout.WorkoutService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.math.log

class WorkoutScreenViewModel: ViewModel() {


    private val _navigationAndStatusEvent = MutableSharedFlow<NavigationEvent>()
    val navigationAndStatusEvent: SharedFlow<NavigationEvent> = _navigationAndStatusEvent

    private val _namePlanilha = MutableLiveData("")
    val namePlanilha: LiveData<String> = _namePlanilha

    private val _spreadsheetList = MutableLiveData<List<SpreadsheetResponse>>()
    val spreadsheetList: LiveData<List<SpreadsheetResponse>> = _spreadsheetList

    private val _nameWorkout = MutableLiveData("")
    val nameWorkout: LiveData<String> = _nameWorkout

    private val _repsWorkout = MutableLiveData("")
    val repsWorkout: LiveData<String> = _repsWorkout

    private val _seriesWorkout = MutableLiveData("")
    val seriesWorkout: LiveData<String> = _seriesWorkout

    private val _weightWorkout = MutableLiveData("")
    val weightWorkout: LiveData<String> = _weightWorkout

    private val _spreadsheetDetail = MutableLiveData<SpreadsheetResponse>()
    val spreadsheetDetail: LiveData<SpreadsheetResponse> = _spreadsheetDetail

    private val _spreadsheetId = MutableLiveData(0)

    fun onChangeNameWorkout(newName: String) {
        _nameWorkout.value = newName
    }

    fun onChangeRepsWorkout(newReps: String) {
        _repsWorkout.value = newReps
    }

    fun onChangeSeriesWorkout(newSeries: String) {
        _seriesWorkout.value = newSeries
    }

    fun onChangeWeightWorkout(newWeight: String) {
        _weightWorkout.value = newWeight
    }

    fun onChangeSpreadsheetId(newSpreadsheetId: Int) {
        _spreadsheetId.value = newSpreadsheetId
    }


    fun onChangeNamePlanilha(novaPlanilha: String) {
        _namePlanilha.value = novaPlanilha
    }


    private val spreadsheet: SpreadsheetService by lazy {
        RetrofitFactory().spreadsheet()
    }

    private val workout: WorkoutService by lazy {
        RetrofitFactory().workout()
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

                val fullToken = "Bearer $token"
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

    fun createWorkout() {
        viewModelScope.launch {

            val name = _nameWorkout.value ?: ""
            val reps = _repsWorkout.value ?: ""
            val series = _seriesWorkout.value ?: ""
            val weight = _weightWorkout.value ?: ""
            val spreadsheetId = _spreadsheetId.value ?: 0

            try {
                val token = PreferencesManager.getToken() ?: run {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Token inválido"))
                    return@launch
                }


                val workoutRequest = WorkoutRequest(
                    name = name,
                    reps = reps.toInt(),
                    series = series.toInt(),
                    weight = weight.toInt(),
                    spreadsheetId = spreadsheetId
                )

                val fullToken = "Bearer $token"
                Log.d("TOKEN", fullToken)
                val response = workout.create(fullToken, workoutRequest)

                if (response.isSuccessful && response.body() != null) {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Exercicio criado com sucesso."))
                }

            } catch (e: HttpException) {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Exceção: ${e.message}"))
            }
        }
    }


    fun spreadsheetByid(id: Int) {
        viewModelScope.launch {
            try {
                val token = PreferencesManager.getToken() ?: return@launch
                val response = spreadsheet.getId("Bearer $token", id)

                if (response.isSuccessful && response.body() != null) {
                    _spreadsheetDetail.postValue(response.body())
                }
            } catch (e: Exception) {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Exceção: ${e.message}"))
                }
            }
        }

    fun removeSpreadsheet(id: Int) {
        viewModelScope.launch {
            try {
                val token = PreferencesManager.getToken() ?: return@launch
                val response = spreadsheet.remove("Bearer $token", id)

                if (response.isSuccessful) {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Planilha removida com sucesso."))
                    viewSpreadsheet()
                } else {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro ao remover a planilha."))
                }
            } catch (e: HttpException) {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro: ${e.message()}"))
            }
        }
    }

    fun removeWorkout(id: Int, idSpreadsheet: Int) {
        viewModelScope.launch {
            try {
                val token = PreferencesManager.getToken() ?: return@launch
                val response = workout.remove("Bearer $token", id)

                if (response.isSuccessful) {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Exercicio removido com sucesso."))
                    spreadsheetByid(idSpreadsheet)
                } else {
                    _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro ao remover o exercicio."))
                }

            } catch (e: HttpException) {
                _navigationAndStatusEvent.emit(NavigationEvent.ShowStatusMessage("Erro: ${e.message()}"))
            }
        }
    }

    fun clearInputs() {
        if (_nameWorkout.value!!.isNotEmpty()) _nameWorkout.value = ""
        if (_repsWorkout.value!!.isNotEmpty()) _repsWorkout.value = ""
        if (_seriesWorkout.value!!.isNotEmpty()) _seriesWorkout.value = ""
        if (_weightWorkout.value!!.isNotEmpty()) _weightWorkout.value = ""
    }


}

sealed class NavigationEvent {
    data class ShowStatusMessage(val message: String) : NavigationEvent()
}