package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountDto(
    @SerializedName("count")
    val count: Long
)
