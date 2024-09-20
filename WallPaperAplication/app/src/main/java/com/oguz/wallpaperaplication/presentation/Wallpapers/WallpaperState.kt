package com.oguz.wallpaperaplication.presentation.Wallpapers

import com.oguz.wallpaperaplication.domain.model.wallpaper

data class WallpaperState(
    val isLoading : Boolean =false,
    val wallpapers : List<wallpaper> = emptyList(),
    val error : String = "",
    val query : String = ""
)