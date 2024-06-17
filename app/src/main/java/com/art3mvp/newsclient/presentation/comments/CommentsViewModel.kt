package com.art3mvp.newsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.usecases.GetCommentsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost,
) : ViewModel() {

    private val repository = NewsFeedRepositoryImpl(application)
    private val getCommentsUseCase = GetCommentsUseCase(repository)

    val screenState: Flow<CommentsScreenState> = getCommentsUseCase(feedPost)
        .map { CommentsScreenState.Comments(feedPost, it) }

}