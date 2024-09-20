package com.oguz.wallpaperaplication.data.repository

import com.oguz.wallpaperaplication.data.remote.WallpaperApi
import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDetailDto
import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDto
import com.oguz.wallpaperaplication.domain.repository.WallPaperRepository
import javax.inject.Inject

class WallPaperRepositoryImpl @Inject constructor(
    private val api : WallpaperApi
) : WallPaperRepository {


    override suspend fun getWallPaper(query: String): WallpaperDto {
        return api.getWallPapers(query = query)
    }

    override suspend fun getWallPaperDetail(id: String): WallpaperDto {
        return api.getWallPapersDetail(id = id)
    }

}