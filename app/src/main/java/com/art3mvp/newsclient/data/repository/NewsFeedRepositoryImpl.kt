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

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    suspend fun loadRecommendations(): List<FeedPost> {
        val startFrom = nextFrom
        Log.d("VVV", startFrom.toString())

        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts

        val response = if (startFrom == null) {
            apiService.loadRecommendation(getAccessToken())
        } else {
            apiService.loadRecommendation(getAccessToken(), startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom

        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
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