package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class PhotosContainerDto(
    @SerializedName("items") val items: List<PhotoDto>
)
