package com.example.gym.service.planilha

import com.example.gym.model.planilha.CreateSpreadsheetRequest
import com.example.gym.model.planilha.SpreadsheetResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface SpreadsheetService {
    @POST("api/Spreadsheet")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body request: CreateSpreadsheetRequest
    ): Response<SpreadsheetResponse>

    @GET("api/Spreadsheet")
    suspend fun view(
        @Header("Authorization") token: String,
    ): Response<SpreadsheetResponse>

}