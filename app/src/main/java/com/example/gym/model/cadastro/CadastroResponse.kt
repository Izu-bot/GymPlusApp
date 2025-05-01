package com.example.gym.model.cadastro

import java.util.UUID

data class CadastroResponse(
    val id: UUID = UUID(0L, 0L),
    val email: String = "",
    val name: String = ""
)
