package com.art3mvp.newsclient.domain.repository

import android.graphics.Bitmap
import kotlinx.coroutines.flow.StateFlow


interface CameraRepository {

    suspend fun reloadGallery()

    fun getBitmapList(): StateFlow<List<Bitmap>>

    suspend fun onTakePhoto(bitmap: Bitmap)

}