package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class PhotoResponseDto(
    @SerializedName("response") val photos: PhotosContainerDto
)
