package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.entity.NewsFeedResult
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetRecommendationsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<NewsFeedResult> {
        return repository.getRecommendations()
    }
}