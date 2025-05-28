package com.example.gym.model.planilha

import com.example.gym.model.treinos.Workout


data class Spreadsheet(
    val id: Int,
    val name: String,
    val workout: List<Workout>
)
