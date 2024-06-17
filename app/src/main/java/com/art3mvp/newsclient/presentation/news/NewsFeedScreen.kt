package com.art3mvp.newsclient.presentation.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.NewsFeedResult
import com.art3mvp.newsclient.presentation.ViewModelFactory


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsFeedScreen(
    viewModelFactory: ViewModelFactory,
    innerPaddingValues: PaddingValues,
    onCommentClickListener: (FeedPost) -> Unit,
) {

    val viewModel: NewsFeedViewModel = viewModel(factory = viewModelFactory)

    val screenState = viewModel.screenState.collectAsState(NewsFeedResult.Loading)

    when (val currentState = screenState.value) {
        is NewsFeedResult.Success -> {

            val pullRefreshState = rememberPullRefreshState(
                refreshing = currentState.refreshing,
                onRefresh = {
                    viewModel.reloadRecommendations()
                }
            )

            Box(
                modifier = Modifier.pullRefresh(pullRefreshState)
            ) {

                FeedPosts(
                    posts = currentState.posts,
                    innerPaddingValues = innerPaddingValues,
                    viewModel = viewModel,
                    onCommentClickListener = onCommentClickListener,
                    nextDataIsLoading = currentState.nextDataLoading,
                )
                PullRefreshIndicator(
                    refreshing = currentState.refreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

        is NewsFeedResult.Loading -> {

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

            FeedPostItem(
                feedPost = feedPost,
                onRemove = viewModel::removeFeedPost,
                viewModel = viewModel,
                onCommentClickListener = onCommentClickListener
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

