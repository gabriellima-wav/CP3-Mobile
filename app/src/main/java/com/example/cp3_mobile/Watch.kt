package com.example.cp3_mobile

data class Watch(
    val id: Long,
    val name: String,
    val description: String,
    val imageResId: Int,
    val brand: String,
    val price: Double,
    val releaseDate: String
)
