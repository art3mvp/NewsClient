package com.art3mvp.newsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art3mvp.newsclient.domain.FeedPost

@Composable
fun NewsFeedScreen(
    innerPaddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit,
) {

    val viewModel: NewsFeedViewModel = viewModel()

    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts,
                innerPaddingValues = innerPaddingValues,
                viewModel = viewModel,
                onCommentClickListener = onCommentClickListener,
                nextDataIsLoading = currentState.nextDataLoading
            )
        }

        NewsFeedScreenState.Loading -> {
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
        else -> {}
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    innerPaddingValues: PaddingValues,
    viewModel: NewsFeedViewModel,
    onCommentClickListener: (FeedPost) -> Unit,
    nextDataIsLoading: Boolean,
) {
    LazyColumn(
        modifier = Modifier.padding(innerPaddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        items(
            items = posts,
            key = { it.id }
        ) { feedPost ->

            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.removeFeedPost(feedPost)
            }


            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                            .background(Color.Red.copy(0.7f))
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = "DELETE POST",
                        )
                    }
                },
                dismissContent = {
                    PostCard(
                        feedPost = feedPost,
                        onShareClickListener = {

                        },
                        onCommentsClickListener = {
                            onCommentClickListener(feedPost)
                            viewModel.getCommentsFromFeedPost(feedPost)
                        },
                        onLikeClickListener = {
                            viewModel.changeLikeStatus(feedPost)
                        },
                    )
                },
                directions = setOf(DismissDirection.EndToStart)
            )
        }
        item {
            if (nextDataIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                }
            } else {
                SideEffect {
                    viewModel.loadNextRecommendations()
                }
            }
        }
    }
}