package com.oguz.wallpaperaplication.presentation.Wallpapers

import android.app.WallpaperManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.oguz.wallpaperaplication.presentation.WallpaperDetail.view.WallpaperDetailFragment
import com.oguz.wallpaperaplication.presentation.Wallpapers.adapter.WallpaperRecyclerAdapter
import com.oguz.wallpaperaplication.presentation.Wallpapers.view.WallpaperFragment
import javax.inject.Inject


class WallpaperFragmentFactory @Inject constructor(
    private val wallpaperRecyclerAdapter : WallpaperRecyclerAdapter,
    private val glide : RequestManager,
    private val wallpaperManager : WallpaperManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            WallpaperFragment::class.java.name -> WallpaperFragment(wallpaperRecyclerAdapter)
            WallpaperDetailFragment::class.java.name -> WallpaperDetailFragment(glide,wallpaperManager)
            else -> super.instantiate(classLoader, className)
        }


    }
}