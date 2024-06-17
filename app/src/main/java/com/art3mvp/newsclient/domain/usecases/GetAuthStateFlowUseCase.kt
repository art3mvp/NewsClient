package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.entity.AuthState
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateFlowUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}