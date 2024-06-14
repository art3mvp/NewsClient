package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val commentsContainer: CommentsContainerDto
)
