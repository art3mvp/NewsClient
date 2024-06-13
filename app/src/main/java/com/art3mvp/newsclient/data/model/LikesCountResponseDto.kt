package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes: LikesCountUpdatedDto
)
