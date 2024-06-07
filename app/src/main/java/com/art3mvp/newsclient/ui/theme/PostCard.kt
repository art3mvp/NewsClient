package com.art3mvp.newsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import com.art3mvp.newsclient.R
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem
import com.art3mvp.newsclient.domain.StatisticType


@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Body(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(feedPost.statistics,
                onViewsClickListener = onViewsClickListener,
                onLikeClickListener = onLikeClickListener,
                onCommentsClickListener = onCommentsClickListener,
                onShareClickListener = onShareClickListener)
        }

    }
}

@Composable
fun Body(feedPost: FeedPost) {
    Text(
        text = feedPost.contentDescription,
        modifier = Modifier.padding(8.dp)
    )
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        painter = painterResource(id = feedPost.contentImageResId),
        contentDescription = "landscape",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.weight(1f)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconAndText(
                iconResId = R.drawable.views,
                value = viewsItem.count.toString(),
                contentDescription = "views"
            ) {
                onViewsClickListener(viewsItem)
            }
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {

            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconAndText(
                iconResId = R.drawable.share,
                value = sharesItem.count.toString(),
                contentDescription = "shares"
            ) {
                onShareClickListener(sharesItem)
            }

            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconAndText(
                iconResId = R.drawable.add_comment,
                value = commentsItem.count.toString(),
                contentDescription = "comments"
            ) {
                onCommentsClickListener(commentsItem)
            }

            val likesItems = statistics.getItemByType(StatisticType.LIKES)
            IconAndText(
                iconResId = R.drawable.heart,
                value = likesItems.count.toString(),
                contentDescription = "likes"
            ) {
                onLikeClickListener(likesItems)
            }
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw RuntimeException("getItemByType error")
}


@Composable
private fun IconAndText(
    value: String,
    iconResId: Int,
    contentDescription: String,
    onItemClickListener: () -> Unit
) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()
        },
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
fun PostHeader(feedPost: FeedPost) {
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
            painter = painterResource(id = feedPost.avatarResId),
            contentDescription = "community logo"
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = feedPost.communityName,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPost.publicationDate,
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
