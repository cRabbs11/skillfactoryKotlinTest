package com.ekochkov.skillfactorykotlintest.viewmodel

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilmPageFragmentViewModel: ViewModel() {

    suspend fun loadBitmapImage(url: String): Bitmap {
        return suspendCoroutine {
            val url = URL(url)
            val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            it.resume(bitmap)
        }
    }

}