package com.oguz.wallpaperaplication.domain.model

data class wallpaperDetail(

    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val user: String,
    val views: Int

)