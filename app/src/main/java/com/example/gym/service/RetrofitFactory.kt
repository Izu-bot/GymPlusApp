package com.example.gym.service

import com.example.gym.service.api.ApiService
import com.example.gym.service.auth.AuthService
import com.example.gym.service.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val _url = "https://9799-2804-dec-236-d500-ae50-3fbb-3370-7ff9.ngrok-free.app/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun authService() : AuthService {
        return retrofitFactory.create(AuthService::class.java)
    }

    fun cadastroUsuario() : UserService {
        return retrofitFactory.create(UserService::class.java)
    }

    fun apiCheck() : ApiService {
        return retrofitFactory.create(ApiService::class.java)
    }
}