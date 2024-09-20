package com.oguz.wallpaperaplication.util

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowMetrics


fun Bitmap.scaleAndSetWallpaper(activity : Activity, wallpaperManager: WallpaperManager){

    val scaledBitmap : Bitmap
    val metrics = DisplayMetrics()

    return try {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){

            val metrics = activity.windowManager.currentWindowMetrics

            scaledBitmap = Bitmap.createScaledBitmap(this, //TODO: this -> Fonksiyon içinde, o anki bitmap nesnesini temsil eder.
                metrics.bounds.width(),
                metrics.bounds.height(),
                false)

            wallpaperManager.suggestDesiredDimensions(metrics.bounds.width(),metrics.bounds.height())



        } else {

            activity.windowManager.defaultDisplay.getMetrics(metrics)

            val phoneHeight = metrics.heightPixels
            val phoneWidth = metrics.widthPixels

            Bitmap.createScaledBitmap(this,phoneWidth,phoneHeight,true)
            wallpaperManager.suggestDesiredDimensions(phoneWidth, phoneHeight)

        }

        wallpaperManager.setWallpaperOffsetSteps(1f,1f)
        wallpaperManager.setBitmap(this) //TODO:this -> yani fonksiyonu çağıran bitmap, artık duvar kağıdı olarak kullanılacaktır.

    }catch (e: Exception) {
        e.printStackTrace()
    } finally {
        // Gerekirse ölçeklenmiş bitmap'i geri dönüşüme al
        if (!this.isRecycled) {
            this.recycle()
        }
    }
}