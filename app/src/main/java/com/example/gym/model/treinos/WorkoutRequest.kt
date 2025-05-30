package com.example.gym.model.treinos

data class WorkoutRequest(
    val name: String,
    val reps: Int,
    val series: Int,
    val weight: Int,
    val spreadsheetId: Int
)
