package com.art3mvp.newsclient.presentation.news

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.art3mvp.newsclient.R
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.StatisticItem
import com.art3mvp.newsclient.domain.entity.StatisticType
import java.util.Locale


@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit
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
            Statistics(
                feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onCommentsClickListener = onCommentsClickListener,
                onShareClickListener = onShareClickListener,
                isFavourite = feedPost.isLiked
            )
        }

    }
}

@Composable
fun Body(feedPost: FeedPost) {
    Text(
        text = feedPost.contentDescription,
        modifier = Modifier.padding(8.dp)
    )
    AsyncImage(
        model = feedPost.contentImageUrl,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onCommentsClickListener: (StatisticItem) -> Unit,
    isFavourite: Boolean,
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Row(modifier = Modifier.weight(1f)) {
            val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
            IconAndText(
                iconResId = R.drawable.views,
                value = formatStatisticCount(viewsItem.count),
                contentDescription = "views"
                )
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val sharesItem = statistics.getItemByType(StatisticType.SHARES)
            IconAndText(
                iconResId = R.drawable.share,
                value = formatStatisticCount(sharesItem.count),
                contentDescription = "shares",
                onItemClickListener =
                {
                    onShareClickListener(sharesItem)
                })


            val commentsItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconAndText(
                iconResId = R.drawable.add_comment,
                value = formatStatisticCount(commentsItem.count),
                contentDescription = "comments",
                onItemClickListener =
                {
                    onCommentsClickListener(commentsItem)
                })


            val likesItems = statistics.getItemByType(StatisticType.LIKES)
            IconAndText(
                iconResId = if (isFavourite) R.drawable.red_heart else R.drawable.heart,
                value = formatStatisticCount(likesItems.count),
                contentDescription = "likes",
                onItemClickListener = {
                    onLikeClickListener(likesItems)
                },
                tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

private fun formatStatisticCount(count: Long): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format(Locale.getDefault(), "%.1fK", (count / 1000f))
    } else {
        count.toString()
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
    onItemClickListener: (() -> Unit)? = null,
    tint: Color = MaterialTheme.colorScheme.onSecondary,
) {

    val modifier = if (onItemClickListener == null) {
        Modifier
    } else {
        Modifier.clickable {
            onItemClickListener()
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = tint
        )
        Spacer(modifier = Modifier.width(3.dp))
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
        AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            contentDescription = null
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
