package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.repository.NewsFeedRepository

class RefreshDataUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke() {
        return repository.refreshData()
    }
}