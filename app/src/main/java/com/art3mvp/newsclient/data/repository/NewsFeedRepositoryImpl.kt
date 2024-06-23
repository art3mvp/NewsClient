package com.art3mvp.newsclient.data.repository

import android.util.Log
import com.art3mvp.newsclient.data.mapper.MainMapper
import com.art3mvp.newsclient.data.network.ApiService
import com.art3mvp.newsclient.domain.entity.AuthState
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.PostComment
import com.art3mvp.newsclient.domain.entity.Profile
import com.art3mvp.newsclient.domain.entity.StatisticItem
import com.art3mvp.newsclient.domain.entity.StatisticType
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import com.art3mvp.newsclient.extensions.mergeWith
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val storage: VKPreferencesKeyValueStorage,
    private val apiService: ApiService,
    private val mapper: MainMapper,
) : NewsFeedRepository {

    private val token
        get() = VKAccessToken.restore(storage)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // Auth
    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)
    private val authStateFlow = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            Log.d("VVV", "Token: ${token?.accessToken}")
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authState = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    //cached posts
    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    //recommendations
    private var nextFrom: String? = null

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
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }
    private val recommendations: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )


    //Profile
    private suspend fun getProfileId(): Long {
        return apiService.getProfileId(getAccessToken()).profileIdContainer.id
    }
    private val profileFlow: StateFlow<Profile?> = flow {
        val profileId = getProfileId()

        val photosResponse = apiService.getProfilePhotos(
            token = getAccessToken(),
            ownerId = profileId
        )
        val userResponse = apiService.getUserInfo(
            token = getAccessToken(),
            ownerId = profileId
        )

        val profile = mapper.mapResponseToProfile(userResponse, photosResponse)
        emit(profile)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = null
    )

    // implementations
    override fun getProfile(): StateFlow<Profile?> = profileFlow

    override suspend fun loadNextData() {
        nextDataNeededEvent.emit(Unit)
    }

    override fun getRecommendations(): StateFlow<List<FeedPost>> = recommendations

    override suspend fun refreshData() {
        _feedPosts.clear()
        Log.d("VVV", "refresh data, list cleared")
        loadNextData()
    }

    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val response = apiService.getComments(
            token = getAccessToken(), ownerId = feedPost.communityId, postId = feedPost.id
        )
        Log.d("VVV", response.toString())
        emit(mapper.mapResponseToPostComments(response))
    }.retry {
        delay(RETRY_TIMEOUT)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun ignorePost(feedPost: FeedPost) {
        val response = apiService.ignorePost(
            token = getAccessToken(), ownerId = feedPost.communityId, itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
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

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    companion object {
        private const val RETRY_TIMEOUT = 3000L
    }
}