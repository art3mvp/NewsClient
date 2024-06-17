package com.art3mvp.newsclient.presentation.news

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.art3mvp.newsclient.domain.entity.FeedPost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPostItem(
    feedPost: FeedPost,
    onRemove: (FeedPost) -> Unit,
    viewModel: NewsFeedViewModel,
    onCommentClickListener: (FeedPost) -> Unit,
) {
    var isVisible by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(feedPost)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isVisible = false
                }

                else -> {
                    return@rememberSwipeToDismissBoxState false
                }
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

    AnimatedVisibility(
        visible = isVisible,
        exit = shrinkVertically()
    ) {
        SwipeToDismissBox(
            state = dismissState,
            backgroundContent = { DismissBackground(dismissState) },
            content = {
                PostCard(
                    feedPost = feedPost,
                    onShareClickListener = {

                    },
                    onCommentsClickListener = {
                        onCommentClickListener(feedPost)
                    },
                    onLikeClickListener = {
                        viewModel.changeLikeStatus(feedPost)
                    },
                )
            },
            enableDismissFromStartToEnd = false
        )
    }
    LaunchedEffect(isVisible) {
        if (!isVisible) {
            onRemove(currentItem)
        }
    }

}