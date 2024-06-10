package com.art3mvp.newsclient.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.art3mvp.newsclient.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector,
) {
    data object Home : NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Rounded.Home
    )

    data object Favorite : NavigationItem(
        screen = Screen.Favourite,
        titleResId = R.string.navigation_item_favorite,
        icon = Icons.Rounded.Favorite
    )

    data object Profile : NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Rounded.AccountBox
    )

}