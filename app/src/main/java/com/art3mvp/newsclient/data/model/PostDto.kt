package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class PostDto(

    @SerializedName("id") val id: Long,
    @SerializedName("source_id") val sourceId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String,
    @SerializedName("views") val views: ViewsCountDto,
    @SerializedName("comments") val comments: CommentsCountDto,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("reposts") val reposts: RepostsCountDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
    ) {
}