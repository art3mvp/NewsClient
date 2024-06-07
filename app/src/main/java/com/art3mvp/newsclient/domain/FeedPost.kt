package com.art3mvp.newsclient.domain

import androidx.compose.ui.graphics.vector.ImageVector
import com.art3mvp.newsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "dev/photo",
    val publicationDate: String = "12:00",
    val avatarResId: Int = R.drawable.photografer,
    val contentDescription: String = "lorem ipsumlorem ipsumlorem ipsumlorem ipsumlorem ipsumlorem ipsum",
    val contentImageResId: Int = R.drawable.landscape,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 131),
        StatisticItem(StatisticType.SHARES, 41),
        StatisticItem(StatisticType.COMMENTS, 44),
        StatisticItem(StatisticType.LIKES, 5)
    )
)