package com.art3mvp.newsclient.presentation.comments

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.art3mvp.newsclient.domain.entity.FeedPost

class CommentsViewModelFactory(
    private val application: Application,
    private val feedPost: FeedPost
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(application, feedPost) as T
    }
}