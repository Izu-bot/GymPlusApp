package com.example.gym.service.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/healt") // Rota raiz ou um endpoint específico como "/health"
    suspend fun ping(): Response<Unit> // Não precisa parsear resposta
}