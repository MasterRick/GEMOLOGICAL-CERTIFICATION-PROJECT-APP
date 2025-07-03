package com.certgem.data.model

data class Gemologist  (
    val id: String,
    val userId: String,
    val publicName: String,
    val phone: String,
    val address: String,
    val professionalLicense: String,
    val certificates: List<Certificate>
)
