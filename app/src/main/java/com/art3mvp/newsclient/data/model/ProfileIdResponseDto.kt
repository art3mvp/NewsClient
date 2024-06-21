package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class ProfileIdResponseDto(
    @SerializedName("response") val profileIdContainer: ProfileIdContainer
)