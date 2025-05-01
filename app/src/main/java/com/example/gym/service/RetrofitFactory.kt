package com.example.gym.service

import com.example.gym.service.auth.AuthService
import com.example.gym.service.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val _url = "https://9286-2804-dec-236-d500-3a93-e4c3-2168-6d81.ngrok-free.app/"

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
}