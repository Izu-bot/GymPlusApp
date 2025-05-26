package com.example.gym.model.planilha

import com.example.gym.model.treinos.WokoutResponse

data class SpreadsheetResponse (
    val id: Int,
    val name: String,
    val workout: List<WokoutResponse>
)