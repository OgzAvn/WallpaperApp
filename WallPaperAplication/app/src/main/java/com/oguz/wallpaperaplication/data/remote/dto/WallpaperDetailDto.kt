package com.oguz.wallpaperaplication.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.oguz.wallpaperaplication.domain.model.wallpaperDetail

data class WallpaperDetailDto(

    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    @SerializedName("user_id")
    val userId: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
)

fun WallpaperDetailDto.toWallPaperDetail() : wallpaperDetail {

    return wallpaperDetail(downloads, id, imageHeight, imageWidth, largeImageURL , likes, user, views)

}