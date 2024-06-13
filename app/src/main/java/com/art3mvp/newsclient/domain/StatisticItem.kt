package com.art3mvp.newsclient.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticItem(
    val type: StatisticType,
    val count: Long = 0
): Parcelable

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}