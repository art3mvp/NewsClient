package com.art3mvp.newsclient.domain

sealed class NewsFeedResult {

    object Loading: NewsFeedResult()

    data class Success(
        val posts: List<FeedPost>,
        val nextDataLoading: Boolean = false
    ): NewsFeedResult()
}