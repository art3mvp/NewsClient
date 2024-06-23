package com.art3mvp.newsclient.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.art3mvp.newsclient.domain.entity.Profile

@Composable
fun ProfileCard(profile: Profile) {
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = profile.coverUrl,
            contentDescription = "cover image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Card(
            modifier = Modifier
                .offset(y = 140.dp),
            elevation = CardDefaults.elevatedCardElevation()
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally),
                text = profile.firstName + "    " + profile.lastName
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = profile.status
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyVerticalGrid(
                contentPadding = PaddingValues(
                    top = 10.dp,
                    bottom = 250.dp
                ),
                columns = GridCells.Fixed(3)
            ) {
                items(profile.images) { imageUrl ->

                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .aspectRatio(1f),
                        elevation = CardDefaults.elevatedCardElevation(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(100.dp)
                .offset(y = 90.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(4.dp)
                .clip(CircleShape)
                .background(Color.White),
            model = profile.avatarUrl,
            contentDescription = null,
        )
    }
}