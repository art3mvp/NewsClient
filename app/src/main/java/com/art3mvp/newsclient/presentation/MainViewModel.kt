package com.art3mvp.newsclient.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem
import com.art3mvp.newsclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem(item: NavigationItem) {
        _selectedNavItem.value = item
    }

    private val initialPosts = mutableListOf<FeedPost>().apply {
        repeat(10) {

            this.add(
                FeedPost(
                    id = it,
                    contentDescription = "content: $it"
                )
            )
        }
    }
    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialPosts)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun updateCount(feedPost: FeedPost, newItem: StatisticItem) {
        val oldPosts = _feedPosts.value?.toMutableList() ?: throw IllegalStateException()

        val newStatistics = feedPost.statistics.map { oldItem ->
            if (oldItem.type == newItem.type) {
                oldItem.copy(count = oldItem.count + 1)
            } else {
                oldItem
            }
        }

        oldPosts.replaceAll {
            if (feedPost.id == it.id) {
                feedPost.copy(statistics = newStatistics)
            } else {
                it
            }
        }

        _feedPosts.value = oldPosts
    }

    fun removeFeedPost(feedPost: FeedPost) {
        val modifierList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifierList.remove(feedPost)
        _feedPosts.value = modifierList

    }
}