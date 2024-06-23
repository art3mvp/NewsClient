package com.art3mvp.newsclient.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art3mvp.newsclient.domain.entity.ProfileState
import com.art3mvp.newsclient.presentation.getApplicationComponent


@Composable
fun ProfileScreen(innerPaddingValues: PaddingValues) {

    val component = getApplicationComponent()
    val viewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())
    val profileState = viewModel.profile.collectAsState(ProfileState.Initial)

    when (val currentState = profileState.value) {
        is ProfileState.Success -> {
            val profile = currentState.profile
            ProfileCard(profile = profile)
        }

        else -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPaddingValues),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    color = Color.Black
                )
            }
        }
    }
}