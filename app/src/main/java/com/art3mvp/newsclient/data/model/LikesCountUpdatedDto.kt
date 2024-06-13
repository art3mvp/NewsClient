package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class LikesCountUpdatedDto(
    @SerializedName("likes") val count: Long
)
