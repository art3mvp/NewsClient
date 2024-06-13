package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class GroupDto(
    @SerializedName("id") val id: Long,
    @SerializedName("screen_name") val name: String ,
    @SerializedName("photo_200") val groupPhoto: String
){

}
