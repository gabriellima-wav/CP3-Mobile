package com.example.cp3_mobile

data class Watch(
    val id: Long,
    val name: String,
    val description: String,
    val imageUri: String, // Usamos String para a URI da imagem
    val brand: String,
    val price: Double,
    val releaseDate: String
)
