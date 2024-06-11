package com.art3mvp.newsclient.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.art3mvp.newsclient.domain.FeedPost

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            this.launchSingleTop = true
            this.restoreState = true

        }
    }

    fun navigateToComments(feedPost: FeedPost) {
        navHostController.navigate(Screen.Comments.getRouteWithArgs(feedPost))
     }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}