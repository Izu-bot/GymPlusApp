package com.example.gym.service

import com.example.gym.model.LoginRequest
import com.example.gym.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/Auth/login")
    suspend fun login(@Body request: LoginRequest) : Response<LoginResponse>
}