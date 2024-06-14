package com.art3mvp.newsclient.data.model

import com.google.gson.annotations.SerializedName

data class CommentsContainerDto(
    @SerializedName("items") val commentsList: List<CommentDto>,
    @SerializedName("profiles") val profilesList: List<ProfileDto>
)