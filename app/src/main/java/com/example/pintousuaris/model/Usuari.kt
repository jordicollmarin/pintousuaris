package com.example.pintousuaris.model

import kotlinx.serialization.SerialName

data class Usuari(
    val id: Int,
    val name: String,
    @SerialName(value = "username")
    val email: String,
)

