package com.art3mvp.newsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.art3mvp.newsclient.ui.theme.NewsClientTheme
import com.art3mvp.newsclient.ui.theme.PostCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsClientTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                ) { innerPadding ->
                    PostCard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}