package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.repository.CameraRepository
import javax.inject.Inject

class ReloadGalleryUseCase @Inject constructor(
    private val repository: CameraRepository
) {
    suspend operator fun invoke() {
        return repository.reloadGallery()
    }
}