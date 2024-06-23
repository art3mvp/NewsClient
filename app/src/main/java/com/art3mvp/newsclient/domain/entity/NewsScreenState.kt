package com.art3mvp.newsclient.domain.entity

sealed class NewsScreenState {

    object Initial: NewsScreenState()

    object Loading: NewsScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataLoading: Boolean = false,
        val refreshing: Boolean = false
    ): NewsScreenState()
}