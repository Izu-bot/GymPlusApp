package com.example.gym.service.workout

import com.example.gym.model.treinos.WorkoutRequest
import com.example.gym.model.treinos.WorkoutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface WorkoutService {

    @POST("api/Workout")
    suspend fun create(@Header("Authorization") token: String, @Body request: WorkoutRequest): Response<WorkoutResponse>

    @DELETE("api/Workout/{id}")
    suspend fun remove(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<Unit>
}