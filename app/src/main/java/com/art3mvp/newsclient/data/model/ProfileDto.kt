package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_200") val avatarUrl: String,
    @SerializedName("first_name") val userName: String
)
