package com.art3mvp.newsclient.presentation.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.art3mvp.newsclient.domain.entity.Profile

@Composable
fun ProfileCard(profile: Profile) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = 180.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ){
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .offset(y = (-50).dp),
            text = profile.firstName + "    " + profile.lastName
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            text = profile.status
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
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
}