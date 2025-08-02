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

data class CertificateResponse(
    val id: Long,
    val dataCriacao: String?,
    val dataEdicao: String?,
    val dataExclusao: String?,

    val numeroCertificado: String,
    val laudoTecnico: String?,
    val observacoes: String?,

    val imagem: String?,

    val especificacao: EspecificacaoResponse,
    val resultado: ResultadoResponse,
    val template: TemplateResponse
)

data class EspecificacaoResponse(
    val id: Long,
    val dataCriacao: String?,
    val dataEdicao: String?,
    val dataExclusao: String?,

    val nome: String,
    val tipo: String,

    val peso: Short,
    val altura: Short,
    val largura: Short,
    val comprimento: Short,

    val cor: String?,
    val corte: String?,
    val transparencia: String?,
    val claridade: String?
)


data class TemplateResponse(
    val id: Long,
    val dataCriacao: String?,
    val dataEdicao: String?,
    val dataExclusao: String?,
    val arquivo: String?
)


data class ResultadoResponse(
    val id: Long,
    val dataCriacao: String?,
    val dataEdicao: String?,
    val dataExclusao: String?,

    val especie: String,
    val variante: String,
    val tratamento: String?,
    val origem: String?
)


