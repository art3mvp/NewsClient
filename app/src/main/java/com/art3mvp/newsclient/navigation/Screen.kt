package com.art3mvp.newsclient.navigation

import android.net.Uri
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.google.gson.Gson

sealed class Screen(
    val route: String,
) {
    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Camera : Screen(ROUTE_CAMERA)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"

        fun getRouteWithArgs(feedPost: FeedPost): String {
            val feedPostGson = Gson().toJson(feedPost)

            return "$ROUTE_FOR_ARGS/${feedPostGson.encode()}"
        }
    }

    companion object {


        const val KEY_FEED_POST = "feed_post"
        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_CAMERA = "camera"
        const val ROUTE_PROFILE = "profile"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}