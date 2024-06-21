package com.art3mvp.newsclient.domain.entity

import android.graphics.Bitmap
import android.os.Parcelable
import com.art3mvp.newsclient.R
import kotlinx.parcelize.Parcelize


data class Profile(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val avatarUrl: String,
    val coverUrl: String,
    val status: String,
    val images: List<String> = emptyList(),
)