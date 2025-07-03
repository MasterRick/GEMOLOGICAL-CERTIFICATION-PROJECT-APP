package com.certgem.data.model

enum class UserType {
    CLIENT, GEMOLOGIST, ADMIN
}

data class User(
    val id: String,
    val name: String,
    val email: String,
    val userType: UserType
)