package com.art3mvp.newsclient.domain.usecases

import android.graphics.Bitmap
import com.art3mvp.newsclient.domain.repository.CameraRepository
import javax.inject.Inject

class TakePhotoUseCase @Inject constructor(
    private val repository: CameraRepository,
) {
    suspend operator fun invoke(bitmap: Bitmap) {
        return repository.onTakePhoto(bitmap)
    }
}