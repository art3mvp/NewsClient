package com.art3mvp.newsclient.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.art3mvp.newsclient.data.mapper.NewsFeedMapper
import com.art3mvp.newsclient.data.network.ApiFactory
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem
import com.art3mvp.newsclient.domain.StatisticType
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken

class NewsFeedRepositoryImpl(application: Application) {

    val storage = VKPreferencesKeyValueStorage(application)
    val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadRecommendations(): List<FeedPost> {
        val response = apiService.loadRecommendation(getAccessToken())
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return posts
    }


    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }


    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        }

        val likesCountUpdated = response.likes.count

        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(StatisticType.LIKES, likesCountUpdated))
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)

        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}