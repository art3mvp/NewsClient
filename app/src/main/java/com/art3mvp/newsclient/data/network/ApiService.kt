package com.art3mvp.newsclient.data.network

import com.art3mvp.newsclient.data.model.NewsFeedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendation(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}