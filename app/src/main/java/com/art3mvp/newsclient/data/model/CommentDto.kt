package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val fromId: Long,
    @SerializedName("post_id") val postId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String,
)
