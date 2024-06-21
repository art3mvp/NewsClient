package com.art3mvp.newsclient.data.network

import com.art3mvp.newsclient.data.model.CommentsResponseDto
import com.art3mvp.newsclient.data.model.IgnoreResponseDto
import com.art3mvp.newsclient.data.model.LikesCountResponseDto
import com.art3mvp.newsclient.data.model.NewsFeedResponseDto
import com.art3mvp.newsclient.data.model.PhotoResponseDto
import com.art3mvp.newsclient.data.model.ProfileIdResponseDto
import com.art3mvp.newsclient.data.model.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendation(
        @Query("access_token") token: String
        ): NewsFeedResponseDto

    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadRecommendation(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.199&type=wall")
    suspend fun ignorePost(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): IgnoreResponseDto

    @GET("wall.getComments?v=5.199&extended=1&fields=photo_200")
    suspend fun getComments(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("post_id") postId: Long
    ): CommentsResponseDto

    @GET("account.getProfileInfo?v=5.199&extended=1")
    suspend fun getProfileId(
        @Query("access_token") token: String
    ): ProfileIdResponseDto


    @GET("photos.get?v=5.199&album_id=profile")
    suspend fun getProfilePhotos(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long
    ): PhotoResponseDto

    @GET("users.get?v=5.199&fields=photo_200,cover,status")
    suspend fun getUserInfo(
        @Query("access_token") token: String,
        @Query("user_ids") ownerId: Long
    ) : UserResponseDto
}