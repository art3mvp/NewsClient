package com.art3mvp.newsclient.presentation.camera

import android.graphics.Bitmap

sealed class CameraScreenState {

    object Initial: CameraScreenState()

    data class Bitmaps(
        val bitmaps: List<Bitmap>
    ): CameraScreenState()
}