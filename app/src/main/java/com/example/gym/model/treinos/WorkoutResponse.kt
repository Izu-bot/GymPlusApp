package com.example.gym.model.treinos

data class WorkoutResponse(
    val id: Int,
    val name: String,
    val reps: Int,
    val series: Int,
    val weight: Int
)
