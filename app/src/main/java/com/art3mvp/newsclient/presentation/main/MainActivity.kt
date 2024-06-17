package com.art3mvp.newsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art3mvp.newsclient.domain.entity.AuthState
import com.art3mvp.newsclient.presentation.NewsFeedApplication
import com.art3mvp.newsclient.presentation.ViewModelFactory
import com.art3mvp.newsclient.ui.theme.NewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
       (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            NewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val launcher
                = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult()
                }

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
                    }

                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}