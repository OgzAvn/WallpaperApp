package com.oguz.wallpaperaplication.presentation.WallpaperDetail

sealed class WallpaperDetailEvent {

    data class GetbyId(val id : String) : WallpaperDetailEvent()

}