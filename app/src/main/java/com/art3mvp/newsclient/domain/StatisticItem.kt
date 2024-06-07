package com.art3mvp.newsclient.domain

data class StatisticItem(
    val type: StatisticType,
    val count:Int = 0
)

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}