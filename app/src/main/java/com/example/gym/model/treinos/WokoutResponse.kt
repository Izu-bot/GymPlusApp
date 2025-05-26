package com.example.gym.model.treinos

data class WokoutResponse(
    val id: Int,
    val name: String,
    val reps: Int,
    val series: Int,
    val weight: Int
)
