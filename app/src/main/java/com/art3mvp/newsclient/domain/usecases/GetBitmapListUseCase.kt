package com.art3mvp.newsclient.domain.usecases

import android.graphics.Bitmap
import com.art3mvp.newsclient.domain.repository.CameraRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetBitmapListUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    operator fun invoke(): StateFlow<List<Bitmap>> {
        return repository.getBitmapList()
    }
}