package com.art3mvp.newsclient.domain

import com.art3mvp.newsclient.R

data class PostComment(
    val id: Long = 0,
    val authorId: Long = 0,
    val avatarUrl: String,
    val authorName: String,
    val commentText: String,
    val publicationDate: String
) {
}