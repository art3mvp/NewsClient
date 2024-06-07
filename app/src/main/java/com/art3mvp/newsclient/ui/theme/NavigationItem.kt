package com.art3mvp.newsclient.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.art3mvp.newsclient.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector,
) {
    data object Home : NavigationItem(
        titleResId = R.string.navigation_item_home,
        Icons.Rounded.Home
    )

    data object Favorite : NavigationItem(
        titleResId = R.string.navigation_item_favorite,
        Icons.Rounded.FavoriteBorder
    )

    data object Profile : NavigationItem(
        titleResId = R.string.navigation_item_profile,
        Icons.Rounded.AccountBox
    )

}