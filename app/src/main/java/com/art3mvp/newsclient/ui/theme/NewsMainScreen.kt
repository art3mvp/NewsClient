package com.art3mvp.newsclient.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.art3mvp.newsclient.navigation.AppNavGraph
import com.art3mvp.newsclient.presentation.MainViewModel


@Composable
fun MainScreen(viewModel: MainViewModel) {

    val navHostController = rememberNavController()


    Scaffold(
        bottomBar = {
            BottomAppBar(
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = { navHostController.navigate(item.screen.route) },
                        icon = { Icon(item.icon, null) },
                        label = { Text(text = stringResource(id = item.titleResId)) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        },
    ) { innerPadding ->
        AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = { HomeScreen(viewModel =viewModel, innerPaddingValues =innerPadding)},
            favouriteScreenContent = { TextCounter("Favorite")},
            profileScreenContent = {TextCounter("Profile")}
        )
    }
}

@Composable
private fun TextCounter(name: String) {
    var count by remember {
        mutableStateOf(0)
    }
    Text(
        modifier = Modifier
            .padding(50.dp)
            .clickable { count++ },
        text = "screen name: $name // count: $count"
    )
}



