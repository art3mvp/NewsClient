package com.art3mvp.newsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.presentation.CommentsViewModel

class CommentsViewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}