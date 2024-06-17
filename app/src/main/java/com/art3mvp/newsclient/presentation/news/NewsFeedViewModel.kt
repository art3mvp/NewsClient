package com.art3mvp.newsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = NewsFeedRepositoryImpl(application)

    private val getRecommendationsUseCase = GetRecommendationsUseCase(repository)
    private val loadNextDataUseCase= LoadNextDataUseCase(repository)
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository)
    private val ignorePostUseCase = IgnorePostUseCase(repository)
    private val refreshDataUseCase = RefreshDataUseCase(repository)


    private val exceptionHandler =
        CoroutineExceptionHandler { _, _ -> Log.d("VVV", "error, something bad") }

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