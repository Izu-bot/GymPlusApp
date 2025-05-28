package com.example.gym.model.planilha

import com.example.gym.model.treinos.WorkoutResponse

data class SpreadsheetResponse (
    val id: Int,
    val name: String,
    val workout: List<WorkoutResponse>
)