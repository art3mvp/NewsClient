package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class IgnoreResponseDto(
    @SerializedName("response") val ignoreStatus: IgnoreStatusDto
)
