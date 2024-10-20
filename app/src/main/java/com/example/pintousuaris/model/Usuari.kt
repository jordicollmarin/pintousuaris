package com.example.pintousuaris.model

import kotlinx.serialization.SerialName

data class Usuari(
    val id: Int,
    val name: String,
    @SerialName(value = "username")
    val userName: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company,
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    @SerialName(value = "zipcode")
    val zipCode: String,
    val geo: Geo,
)

data class Geo(
    val lat: String,
    val lng: String,
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String,
)