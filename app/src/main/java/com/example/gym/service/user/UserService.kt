package com.example.gym.service.user

import com.example.gym.model.cadastro.CadastroRequest
import com.example.gym.model.cadastro.CadastroResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("api/User")
    suspend fun add(@Body request: CadastroRequest) : Response<CadastroResponse>

    @GET("api/User")
    suspend fun getId(@Header("Authorization") authHeader: String): Response<CadastroResponse>
}