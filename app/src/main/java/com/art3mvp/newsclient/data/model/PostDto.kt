package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(

    @SerializedName("id") val id: String,
    @SerializedName("source_id") val sourceId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("is_favorite") val isFavorite: Boolean,
    @SerializedName("text") val text: String,
    @SerializedName("views") val views: ViewsCountDto,
    @SerializedName("comments") val comments: CommentsCountDto,
    @SerializedName("likes") val likes: LikesCountDto,
    @SerializedName("reposts") val reposts: RepostsCountDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
    ) {
}