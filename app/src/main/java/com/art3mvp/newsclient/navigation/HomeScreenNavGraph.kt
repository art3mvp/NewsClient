package com.art3mvp.newsclient.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.art3mvp.newsclient.domain.FeedPost

fun NavGraphBuilder.homeScreenNavGraph(

    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit,
) {

    navigation(
        startDestination = Screen.NewsFeed.route, route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }

        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST_ID) { type = NavType.IntType },
                navArgument(Screen.KEY_CONTENT_DESCRIPTION) { type = NavType.StringType })
        ) { //comments/{feed_post_id}
            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
            val feedPostDescription = it.arguments?.getString(Screen.KEY_CONTENT_DESCRIPTION) ?: ""
            Log.d("VVV", "${it.arguments}")

            commentsScreenContent(FeedPost(id = feedPostId, contentDescription = feedPostDescription))
        }
    }
}