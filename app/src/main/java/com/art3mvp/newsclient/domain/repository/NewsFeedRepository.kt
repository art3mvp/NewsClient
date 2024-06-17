package com.art3mvp.newsclient.domain.repository

import com.art3mvp.newsclient.domain.entity.AuthState
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.NewsFeedResult
import com.art3mvp.newsclient.domain.entity.PostComment
import kotlinx.coroutines.flow.StateFlow

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getRecommendations(): StateFlow<NewsFeedResult>

    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun refreshData()

    suspend fun ignorePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)
}