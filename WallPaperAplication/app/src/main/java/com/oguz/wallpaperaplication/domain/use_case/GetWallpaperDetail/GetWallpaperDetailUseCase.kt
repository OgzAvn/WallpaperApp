package com.oguz.wallpaperaplication.domain.use_case.GetWallpaperDetail

import android.util.Log
import com.oguz.wallpaperaplication.data.remote.dto.toWallPaperDetail
import com.oguz.wallpaperaplication.data.remote.dto.toWallpaperList
import com.oguz.wallpaperaplication.domain.model.wallpaperDetail
import com.oguz.wallpaperaplication.domain.repository.WallPaperRepository
import com.oguz.wallpaperaplication.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError

import javax.inject.Inject

class GetWallpaperDetailUseCase @Inject constructor(
    private val repository : WallPaperRepository
) {

    fun executeGetWallpaperDetail(id : String) : Flow<Resource<wallpaperDetail>> = flow {


        try {

            emit(Resource.Loading())

            val wallpaper = repository.getWallPaperDetail(id)

            /*
            wallpaper.wallpaperDetailDto.map {
                Log.d("LargeImageUrl :" , it.largeImageURL)
            }
             */

            if (wallpaper.wallpaperDetailDto.isNotEmpty()){

                emit(Resource.Success(wallpaper.toWallPaperDetail()))

            }else{
                emit(Resource.Error("NetworkError"))
            }


        }catch (e:IOError){
            emit(Resource.Error(e.message ?: " NetworkError"))
        }
    }

}