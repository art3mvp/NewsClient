package com.art3mvp.newsclient.presentation.comments

import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.domain.entity.CommentsScreenState
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    val data = getCommentsUseCase(feedPost)
    val screenState: Flow<CommentsScreenState> = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(feedPost, it) }

}