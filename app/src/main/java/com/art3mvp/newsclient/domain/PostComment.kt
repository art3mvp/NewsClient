package com.art3mvp.newsclient.domain

import com.art3mvp.newsclient.R

data class PostComment(
    val id: Int = 0,
    val authorId: Int = 0,
    val avatarResId: Int = R.drawable.photografer,
    val authorName: String = "name",
    val commentText: String = "comment text",
    val publicationDate: String = "12:00"
) {
}