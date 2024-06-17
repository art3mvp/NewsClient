package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.entity.AuthState
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAuthStateFlowUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState> {
        return repository.getAuthStateFlow()
    }
}