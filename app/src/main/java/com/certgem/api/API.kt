package com.certgem.api

data class LoginRequest(
    val email: String,
    val senha: String
)

data class CadastroRequest(
    val nome: String,
    val email: String,
    val senha: String
)

data class LoginResponse(
    val token: String,
    val userId: Long
)
