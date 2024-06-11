package com.art3mvp.newsclient.domain

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import com.art3mvp.newsclient.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable  {

    companion object {

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false) {
            override fun get(bundle: Bundle, key: String): FeedPost? {
               return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                   bundle.getParcelable(key, FeedPost::class.java)
               } else {
                  bundle.getParcelable(key)
               }
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson(value, FeedPost::class.java)
            }

            override fun put(bundle: Bundle, key: String, value: FeedPost) {
                bundle.putParcelable(key,value)
            }
        }
    }
}