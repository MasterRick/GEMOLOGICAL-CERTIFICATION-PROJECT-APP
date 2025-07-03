package com.certgem.model

data class Gemologist(
    val id: String,
    val name: String,
    val address: String,
    val distanceFromUser: Double // em km, para ordenação
)