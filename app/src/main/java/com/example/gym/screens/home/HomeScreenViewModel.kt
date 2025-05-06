package com.example.gym.screens.home

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {
    private val _nameUser = MutableLiveData("")
    val nameUser: LiveData<String> = _nameUser
    val welcome = welcomeUser() + ", " + nameUser

    private fun getHora(): Pair<Int, Int> {
        val agora = Calendar.getInstance()

        val hora = agora.get(Calendar.HOUR_OF_DAY)
        val minutos = agora.get(Calendar.MINUTE)

        return Pair(hora, minutos)

    }

    private fun welcomeUser(): String {
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