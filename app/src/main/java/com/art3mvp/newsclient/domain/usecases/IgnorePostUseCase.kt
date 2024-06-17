package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository

class IgnorePostUseCase(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.ignorePost(feedPost)
    }
}