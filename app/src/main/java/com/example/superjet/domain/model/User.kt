package com.example.superjet.domain.model

data class User(
    val userId: String = "",
    val name : String = "",
    val address: String = "",
    val phone : String = "",
    var profileImage: String = ""
)
