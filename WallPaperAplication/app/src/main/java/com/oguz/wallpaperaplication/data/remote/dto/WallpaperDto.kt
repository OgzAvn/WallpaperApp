package com.oguz.wallpaperaplication.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.oguz.wallpaperaplication.domain.model.wallpaper
import com.oguz.wallpaperaplication.domain.model.wallpaperDetail

data class WallpaperDto(
    @SerializedName("hits")
    val wallpaperDetailDto: List<WallpaperDetailDto>,
    val total: Int,
    val totalHits: Int
)

//list of wallpaper

fun WallpaperDto.toWallpaperList() : List<wallpaper> {

    return wallpaperDetailDto.map {wallpaperDetailDto ->
        wallpaper(wallpaperDetailDto.id,wallpaperDetailDto.previewHeight,wallpaperDetailDto.previewURL,wallpaperDetailDto.previewWidth)
    }

}


fun WallpaperDto.toWallPaperDetail() : wallpaperDetail{

    return wallpaperDetail(

        wallpaperDetailDto[0].downloads,
        wallpaperDetailDto[0].id,
        wallpaperDetailDto[0].imageHeight,
        wallpaperDetailDto[0].imageWidth,
        wallpaperDetailDto[0].largeImageURL,
        wallpaperDetailDto[0].likes,
        wallpaperDetailDto[0].user,
        wallpaperDetailDto[0].views

    )


}


