package com.oguz.wallpaperaplication.domain.use_case.get_wallpaper

import com.oguz.wallpaperaplication.data.remote.dto.toWallpaperList
import com.oguz.wallpaperaplication.domain.model.wallpaper
import com.oguz.wallpaperaplication.domain.repository.WallPaperRepository
import com.oguz.wallpaperaplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetWallPapersUseCase @Inject constructor(
    private val repository: WallPaperRepository
){

    fun executeGetWallPaper(query : String) : Flow<Resource<List<wallpaper>>> = flow {

        try {

            emit(Resource.Loading())

            val wallpaper = repository.getWallPaper(query = query)

            if (wallpaper.wallpaperDetailDto.isNotEmpty()){

                emit(Resource.Success(wallpaper.toWallpaperList()))
            }else{
                emit(Resource.Error("Couldn't found any wallpaper"))
            }

        }catch (e: IOError){

            emit(Resource.Error("Network Error"))

        }


    }


}