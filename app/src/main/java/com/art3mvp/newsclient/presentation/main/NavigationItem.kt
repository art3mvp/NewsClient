package com.art3mvp.newsclient.presentation.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.art3mvp.newsclient.R
import com.art3mvp.newsclient.navigation.Screen

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

    data object Camera : NavigationItem(
        screen = Screen.Camera,
        titleResId = R.string.camera,
        icon = Icons.Rounded.CameraAlt
    )

    data object Profile : NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Rounded.AccountBox
    )

}