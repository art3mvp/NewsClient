package com.art3mvp.newsclient.domain.entity

import android.graphics.Bitmap

sealed class CameraScreenState {

    object Initial: CameraScreenState()

    data class Bitmaps(
        val bitmaps: List<Bitmap>
    ): CameraScreenState()
}