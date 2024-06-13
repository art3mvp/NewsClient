package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class IgnoreStatusDto(
    @SerializedName("status") val status: Boolean
)
