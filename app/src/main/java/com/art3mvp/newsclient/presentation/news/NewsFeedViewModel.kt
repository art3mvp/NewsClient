package com.art3mvp.newsclient.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.NewsFeedResult
import com.art3mvp.newsclient.domain.usecases.ChangeLikeStatusUseCase
import com.art3mvp.newsclient.domain.usecases.GetRecommendationsUseCase
import com.art3mvp.newsclient.domain.usecases.IgnorePostUseCase
import com.art3mvp.newsclient.domain.usecases.LoadNextDataUseCase
import com.art3mvp.newsclient.domain.usecases.RefreshDataUseCase
import com.art3mvp.newsclient.extensions.mergeWith
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getRecommendationsUseCase: GetRecommendationsUseCase,
            private val loadNextDataUseCase: LoadNextDataUseCase,
            private val changeLikeStatusUseCase: ChangeLikeStatusUseCase,
            private val ignorePostUseCase: IgnorePostUseCase,
            private val refreshDataUseCase: RefreshDataUseCase
) : ViewModel() {

    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ ->
            Log.d("VVV", "connection Error")
        }

    private val recommendationsFlow = getRecommendationsUseCase()

    private val loadNextDataFlow = MutableSharedFlow<NewsFeedResult>()

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
            loadNextDataUseCase()
        }
    }

    fun reloadRecommendations() {
        viewModelScope.launch {
            when (recommendationsFlow.value) {
                is NewsFeedResult.Success -> {
                    val stateSuccess = recommendationsFlow.value as NewsFeedResult.Success
                    Log.d("VVV", "reload recommendations with refresh value ${stateSuccess.refreshing} and size ${stateSuccess.posts.size}")
                    loadNextDataFlow.emit(
                        NewsFeedResult.Success(
                            posts = stateSuccess.posts,
                            refreshing = true
                        )
                    )
                }
                else -> {}
            }
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