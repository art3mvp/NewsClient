package com.art3mvp.newsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.PostComment
import kotlinx.coroutines.launch

class CommentsViewModel(
    application: Application,
    feedPost: FeedPost
): ViewModel() {


    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val screenState: LiveData<CommentsScreenState> = _screenState

    private val repository = NewsFeedRepositoryImpl(application)

    init {
        loadComments(feedPost)
    }


    private fun loadComments(feedPost: FeedPost) {
        viewModelScope.launch {
            val comments = mutableListOf<PostComment>().apply {
                addAll(repository.getComments(feedPost))
            }
            _screenState.value = CommentsScreenState.Comments(feedPost, comments)
        }
    }
}