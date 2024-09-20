package com.oguz.wallpaperaplication.data.di

import android.app.WallpaperManager
import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.oguz.wallpaperaplication.data.remote.WallpaperApi
import com.oguz.wallpaperaplication.data.repository.WallPaperRepositoryImpl
import com.oguz.wallpaperaplication.domain.repository.WallPaperRepository
import com.oguz.wallpaperaplication.util.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWallpaperApi() : WallpaperApi{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WallpaperApi::class.java)

    }

    @Provides
    @Singleton
    fun provideWallpaperRepository(api : WallpaperApi) : WallPaperRepository {
        return WallPaperRepositoryImpl(api = api)
    }


    private fun placeHolderProgressBar(context: Context): CircularProgressDrawable {

        return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        }
    }

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context)= Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(placeHolderProgressBar(context))
                .transform(CenterCrop(), RoundedCorners(12))
        )



    @Provides
    @Singleton
    fun injectWallpaperManager(@ApplicationContext context: Context)= WallpaperManager.getInstance(context)


}