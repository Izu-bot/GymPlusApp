package com.example.gym.navigation

import com.example.gym.R

enum class Destination(val route: String, val label: String, val icon: Int) {
    HOME("home", "Inicio", R.drawable.home),
    WORKOUT("workout", "Treinos", R.drawable.ecg_heart),
    CREATE_SPREADSHEET("create_spreadsheet", "Create_spreadsheet", R.drawable.add_80dp),
    CREATE_WORKOUT("create_workout", "Create_workout", R.drawable.add_80dp),
    DETAILS("details/{spreadsheetId}", "Details", R.drawable.add_80dp),
    PHOTOS("photos", "Fotos", R.drawable.photo),
    MEDICAO("medicao", "Medição", R.drawable.straighten),
    LOGIN("login", "Entrar", R.drawable.person),
    CADASTRO("cadastro", "Cadastrar", R.drawable.person_add),
    LOADING("loading", "Carregando",R.drawable.progress);
}