package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    @SerializedName("sizes") val urls: List<PhotoUrlDto>
)
