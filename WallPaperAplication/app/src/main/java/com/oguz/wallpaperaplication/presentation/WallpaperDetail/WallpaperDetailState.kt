package com.oguz.wallpaperaplication.presentation.WallpaperDetail

import com.oguz.wallpaperaplication.domain.model.wallpaperDetail

data class WallpaperDetailState(

    val isLoading : Boolean = false,
    val wallpaperDetail : wallpaperDetail? = null,
    val wallpaperId : String? = "",
    val error : String = ""
)