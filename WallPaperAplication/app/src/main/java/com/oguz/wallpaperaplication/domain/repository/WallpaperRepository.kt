package com.oguz.wallpaperaplication.domain.repository

import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDetailDto
import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDto

interface WallPaperRepository {

    suspend fun getWallPaper(query : String) : WallpaperDto

    suspend fun getWallPaperDetail(id : String) : WallpaperDto

}