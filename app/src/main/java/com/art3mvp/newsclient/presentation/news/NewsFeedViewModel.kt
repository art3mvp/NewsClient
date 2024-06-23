package com.art3mvp.newsclient.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.NewsScreenState
import com.art3mvp.newsclient.domain.usecases.ChangeLikeStatusUseCase
import com.art3mvp.newsclient.domain.usecases.GetRecommendationsUseCase
import com.art3mvp.newsclient.domain.usecases.IgnorePostUseCase
import com.art3mvp.newsclient.domain.usecases.LoadNextDataUseCase
import com.art3mvp.newsclient.domain.usecases.RefreshDataUseCase
import com.art3mvp.newsclient.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
    private val ignorePostUseCase: IgnorePostUseCase,
    private val refreshDataUseCase: RefreshDataUseCase,
) : ViewModel() {

    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            Log.d("NEWSFEEDVIEWMODEL", "connection Error")
        }

    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<NewsScreenState>()

    val screenState = recommendationsFlow
        .filter { it.isNotEmpty() }
        .map { NewsScreenState.Posts(posts = it) as NewsScreenState }
        .onStart { NewsScreenState.Loading }
        .mergeWith(loadNextDataFlow)


    fun loadNextRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsScreenState.Posts(
                    posts = recommendationsFlow.value,
                    nextDataLoading = true
                )
            )
            loadNextDataUseCase()
        }
    }

    fun reloadRecommendations() {
        viewModelScope.launch {
            loadNextDataFlow.emit(
                NewsScreenState.Posts(
                    posts = recommendationsFlow.value,
                    refreshing = true
                )
            )
            refreshDataUseCase()
        }
    }


    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            changeLikeStatusUseCase(feedPost)
        }
    }

    fun removeFeedPost(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) {
            ignorePostUseCase(feedPost)
        }
    }
}