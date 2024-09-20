package com.oguz.wallpaperaplication.presentation.Wallpapers

sealed class WallpaperEvent {

    data class Search(
        val searchQuery : String
    ) : WallpaperEvent()

}