package com.example.gym.service

import com.example.gym.service.api.ApiService
import com.example.gym.service.auth.AuthService
import com.example.gym.service.planilha.SpreadsheetService
import com.example.gym.service.user.UserService
import com.example.gym.service.workout.WorkoutService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val _url = "https://a04c-2804-dec-236-d500-9da1-3e8f-26f6-123e.ngrok-free.app/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun authService() : AuthService {
        return retrofitFactory.create(AuthService::class.java)
    }

    fun user() : UserService {
        return retrofitFactory.create(UserService::class.java)
    }

    fun spreadsheet() : SpreadsheetService {
        return retrofitFactory.create(SpreadsheetService::class.java)
    }

    fun workout() : WorkoutService {
        return retrofitFactory.create(WorkoutService::class.java)
    }

    fun apiCheck() : ApiService {
        return retrofitFactory.create(ApiService::class.java)
    }
}