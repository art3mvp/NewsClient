package com.art3mvp.newsclient.presentation.news

import com.art3mvp.newsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataLoading: Boolean = false,
    ) : NewsFeedScreenState()
}