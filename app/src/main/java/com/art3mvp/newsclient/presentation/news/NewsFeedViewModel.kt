package com.art3mvp.newsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {


    private val initialPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {

            this.add(
                FeedPost(
                    id = it,
                    contentDescription = "content/$it"
                )
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(initialPosts)
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState


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