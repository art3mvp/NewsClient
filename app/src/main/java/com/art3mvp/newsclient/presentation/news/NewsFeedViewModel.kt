package com.art3mvp.newsclient.presentation.news

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {


    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState


    private val repository = NewsFeedRepositoryImpl(application)

    init {
        loadRecommendations()
    }


    private fun loadRecommendations() {
        viewModelScope.launch {
            delay(5000)
            val feedPosts = repository.loadRecommendations()
            _screenState.value = NewsFeedScreenState.Posts(feedPosts)
        }
    }

    fun loadNextRecommendations() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataLoading = true
        )
        loadRecommendations()
    }

    fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            repository.changeLikeStatus(feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }
    }

    fun updateCount(feedPost: FeedPost, newItem: StatisticItem) {

        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()

        val newStatistics = feedPost.statistics.map { oldItem ->
            if (oldItem.type == newItem.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }

        val newPosts = oldPosts.apply {
            replaceAll {
                if (feedPost.id == it.id) {
                    feedPost.copy(statistics = newStatistics)
                } else {
                    it
                }
            }
        }

        _screenState.value = NewsFeedScreenState.Posts(newPosts)
    }

    fun removeFeedPost(feedPost: FeedPost) {

        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

        val modifierList = currentState.posts.toMutableList()
        modifierList.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(modifierList)
    }

}