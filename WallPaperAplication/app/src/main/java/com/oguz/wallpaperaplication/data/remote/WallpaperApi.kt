package com.oguz.wallpaperaplication.data.remote

import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDetailDto
import com.oguz.wallpaperaplication.data.remote.dto.WallpaperDto
import com.oguz.wallpaperaplication.util.Constant.API_KEY
import com.oguz.wallpaperaplication.util.Constant.CATEGORY
import com.oguz.wallpaperaplication.util.Constant.IMAGE_TYPE
import com.oguz.wallpaperaplication.util.Constant.ORDER
import com.oguz.wallpaperaplication.util.Constant.ORIENTATION
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale


interface WallpaperApi {



    @GET("/api/")
    suspend fun getWallPapers(

        @Query("key") apiKey : String = API_KEY,
        @Query("q") query: String?= null,
        @Query("order") order : String = ORDER,
        @Query("lang") lang : String = Locale.getDefault().language,
        @Query("category") category: String = CATEGORY,
        @Query("image_type") imageType: String = IMAGE_TYPE,
        @Query("orientation") orientation: String = ORIENTATION,
        @Query("per_page") perPage: Int = 180

    ) : WallpaperDto


    @GET("/api/")
    suspend fun getWallPapersDetail(

        @Query("id") id: String,
        @Query("key") apiKey: String = API_KEY,
        @Query("image_type") imageType: String = IMAGE_TYPE,
        @Query("orientation") orientation: String = ORIENTATION,

        ) : WallpaperDto



}