package com.art3mvp.newsclient.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.domain.entity.CameraScreenState
import com.art3mvp.newsclient.domain.usecases.GetBitmapListUseCase
import com.art3mvp.newsclient.domain.usecases.ReloadGalleryUseCase
import com.art3mvp.newsclient.domain.usecases.TakePhotoUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val getBitmapListUseCase: GetBitmapListUseCase,
    private val takePhotoUseCase: TakePhotoUseCase,
    private val reloadGalleryUseCase: ReloadGalleryUseCase,
) : ViewModel() {

    val bitmaps: Flow<CameraScreenState> = getBitmapListUseCase()
        .onStart { CameraScreenState.Bitmaps(emptyList()) }
        .map { CameraScreenState.Bitmaps(it) as CameraScreenState }

    fun onTakePhoto(bitmap: Bitmap) {
        viewModelScope.launch {
            takePhotoUseCase(bitmap)
        }
    }

    fun reloadGallery() {
        viewModelScope.launch {
            reloadGalleryUseCase()
        }
    }


}