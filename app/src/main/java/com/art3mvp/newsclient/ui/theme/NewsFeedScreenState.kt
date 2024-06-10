package com.art3mvp.newsclient.ui.theme

import com.art3mvp.newsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial: NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()
}