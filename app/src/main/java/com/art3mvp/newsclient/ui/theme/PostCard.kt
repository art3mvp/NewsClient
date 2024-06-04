package com.art3mvp.newsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.art3mvp.newsclient.R


@Composable
fun PostCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader()
            Spacer(modifier = Modifier.height(8.dp))
            Body()
            Spacer(modifier = Modifier.height(8.dp))
            Statistics()
        }

    }
}

@Composable
fun Body() {
    Text(
        text = stringResource(R.string.trip),
        modifier = Modifier.padding(8.dp)
    )
    Image(
        modifier = Modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.landscape),
        contentDescription = "landscape",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Statistics() {
    Row(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.weight(1f)) {
            IconAndText(iconResId = R.drawable.views, value ="313", contentDescription = "views")
        }

        Row(modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween) {

            IconAndText(iconResId = R.drawable.share, value ="1", contentDescription = "share")
            IconAndText(iconResId = R.drawable.add_comment, value ="14", contentDescription = "comment")
            IconAndText(iconResId = R.drawable.heart, value ="27", contentDescription = "like")

        }
    }
}


@Composable
private fun IconAndText(
    value: String,
    iconResId: Int,
    contentDescription: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun PostHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.photografer),
            contentDescription = "community logo"
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "landscapes group",
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "13:37",
                color = MaterialTheme.colorScheme.onSecondary

            )
        }

        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "edit",
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
fun PreviewLight() {
    NewsClientTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
fun PreviewDark() {
    NewsClientTheme(darkTheme = true) {
        PostCard()
    }
}