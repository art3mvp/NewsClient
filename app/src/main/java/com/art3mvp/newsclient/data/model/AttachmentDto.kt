package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class AttachmentDto(
   @SerializedName("photo") val photo: PhotoDto?
)
