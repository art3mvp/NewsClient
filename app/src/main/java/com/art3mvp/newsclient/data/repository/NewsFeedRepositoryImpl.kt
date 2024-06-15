package com.art3mvp.newsclient.data.repository

import android.app.Application
import android.util.Log
import com.art3mvp.newsclient.data.mapper.MainMapper
import com.art3mvp.newsclient.data.network.ApiFactory
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.PostComment
import com.art3mvp.newsclient.domain.StatisticItem
import com.art3mvp.newsclient.domain.StatisticType
import com.art3mvp.newsclient.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn

class NewsFeedRepositoryImpl(application: Application) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = MainMapper()

    private var nextFrom: String? = null

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val nextDataNeededEvent = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedListFlow = flow {
        nextDataNeededEvent.emit(Unit)
        nextDataNeededEvent.collect {
            val startFrom = nextFrom
            Log.d("VVV", "startFrom: $startFrom")

            if (startFrom == null && feedPosts.isNotEmpty()) {
                this.emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadRecommendation(getAccessToken())
            } else {
                apiService.loadRecommendation(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            this.emit(feedPosts)
        }
    }
        .retry() {
        delay(RETRY_TIMEOUT)
        true
    }

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    suspend fun loadNextData() {
        nextDataNeededEvent.emit(Unit)
    }

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>> = flow {
        val response = apiService.getComments(
            token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
        )
        Log.d("VVV", response.toString())
        emit(mapper.mapResponseToPostComments(response))
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    suspend fun ignorePost(feedPost: FeedPost) {
        val response = apiService.ignorePost(
            token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
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
        refreshedListFlow.emit(feedPosts)
    }

    companion object {
        private const val RETRY_TIMEOUT = 5000L
    }
}