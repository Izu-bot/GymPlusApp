package com.example.gym.navigation

import com.example.gym.R

enum class Destination(val route: String, val label: String, val icon: Int) {
    HOME("home", "Inicio", R.drawable.home),
    WORKOUT("workout", "Treinos", R.drawable.ecg_heart),
    LOGIN("login", "Entrar", R.drawable.person),
    CADASTRO("cadastro", "Cadastrar", R.drawable.person_add),
    LOADING("loading", "Carregando",R.drawable.progress);

}