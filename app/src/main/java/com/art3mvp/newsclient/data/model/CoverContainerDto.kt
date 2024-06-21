package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class CoverContainerDto(
    @SerializedName("images") val covers: List<PhotoUrlDto>
)
