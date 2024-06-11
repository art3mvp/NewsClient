package com.art3mvp.newsclient.presentation.comments

import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.PostComment

sealed class CommentsScreenState {

    object Initial: CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>,
    ) : CommentsScreenState()

}