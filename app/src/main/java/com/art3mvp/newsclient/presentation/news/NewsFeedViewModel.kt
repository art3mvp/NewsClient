package com.art3mvp.newsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.NewsFeedResult
import com.art3mvp.newsclient.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = NewsFeedRepositoryImpl(application)
    private val exceptionHandler =
        CoroutineExceptionHandler { _,_ -> Log.d("VVV", "error, something bad")}

    private val recommendationsFlow = repository.recommendations

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedResult>()

    private val _refreshingState = MutableStateFlow(false)
    val refreshingState = _refreshingState.asStateFlow()

    val screenState = recommendationsFlow
        .onStart { NewsFeedResult.Loading }
        .mergeWith(loadNextDataFlow)


    fun loadNextRecommendations() {
        Log.d("VVV", "loadNextWasTriggered")
        viewModelScope.launch {
            when (recommendationsFlow.value) {
                is NewsFeedResult.Success -> {
                    val stateSuccess = recommendationsFlow.value as NewsFeedResult.Success
                    loadNextDataFlow.emit(
                        NewsFeedResult.Success(
                            posts = stateSuccess.posts,
                            nextDataLoading = true
                        )
                    )
                }
                else -> {}
            }
            repository.loadNextData()
        }
    }

    fun reloadRecommendations() {
        viewModelScope.launch {
            _refreshingState.emit(true)
            repository.refreshData()
            _refreshingState.emit(false)
        }
    }


    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.changeLikeStatus(feedPost)
        }
    }

    fun removeFeedPost(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            repository.ignorePost(feedPost)
        }
    }
}