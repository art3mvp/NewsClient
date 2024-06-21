package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName
import com.vk.sdk.api.status.dto.StatusStatusDto

data class UserContainerDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_200") val avatarUrl: String,
    @SerializedName("cover") val coverContainer: CoverContainerDto,
    @SerializedName("status") val status: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)