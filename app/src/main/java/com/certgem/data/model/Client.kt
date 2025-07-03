package com.certgem.data.model

data class Client(
    val id: String,
    val userId: String,
    val phone: String,
    val address: String,
    val certificates: List<Certificate>
)
