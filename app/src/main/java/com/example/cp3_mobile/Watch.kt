package com.example.cp3_mobile

data class Watch(
    val id: Long,
    val name: String,
    val description: String,
    // Adicione este campo se tiver imagens
    val imageResId: Int = 0
)
