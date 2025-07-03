package com.certgem.model

import java.util.Date

data class Certificate(
    val id: String,
    val number: String,
    val emissionDate: Date,
    // Especificações da Gema
    val gemName: String,
    val item: String,
    val weight: String, // ex: "5.25 ct"
    val measurements: String, // ex: "10.1 x 8.2 x 5.3 mm"
    val shape: String,
    val color: String,
    val transparency: String,
    val clarity: String,
    // Resultado da Avaliação
    val species: String,
    val variety: String,
    val treatment: String,
    val origin: String,
    // Outros
    val comments: String,
    val observations: String,
    val owner: String
)