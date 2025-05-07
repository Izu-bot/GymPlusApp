package com.example.gym.service

import com.example.gym.service.auth.AuthService
import com.example.gym.service.user.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val _url = "https://53e4-2804-dec-236-d500-40e7-c800-115e-eb6f.ngrok-free.app/"

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