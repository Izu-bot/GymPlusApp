package com.example.gym.service

import com.example.gym.service.api.ApiService
import com.example.gym.service.auth.AuthService
import com.example.gym.service.planilha.SpreadsheetService
import com.example.gym.service.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitFactory {
    private val _url = "https://4033-2804-dec-236-d500-6aef-4c72-c403-c4f.ngrok-free.app/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun authService() : AuthService {
        return retrofitFactory.create(AuthService::class.java)
    }

//    TODO: Mudar o nome da função para "usuario"
    fun cadastroUsuario() : UserService {
        return retrofitFactory.create(UserService::class.java)
    }

    fun spreadsheet() : SpreadsheetService {
        return retrofitFactory.create(SpreadsheetService::class.java)
    }

    fun apiCheck() : ApiService {
        return retrofitFactory.create(ApiService::class.java)
    }
}