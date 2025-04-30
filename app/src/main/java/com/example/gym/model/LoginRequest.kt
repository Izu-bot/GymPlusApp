package com.example.gym.model

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    val email: String = "",
    @SerializedName("password") val senha: String = ""
)