package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesDto(
    @SerializedName("count") val count: Long,
    @SerializedName("user_likes") val isLikedByUser: Int
)
