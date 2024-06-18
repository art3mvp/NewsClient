package com.art3mvp.newsclient.presentation.comments

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.art3mvp.newsclient.R
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.PostComment
import com.art3mvp.newsclient.presentation.getApplicationComponent


@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost,
) {

    val component = getApplicationComponent()
        .getCommentsScreenComponentFactory()
        .create(feedPost)

    val viewModel: CommentsViewModel = viewModel(factory = component.getViewModelFactory())

    val screenState = viewModel.screenState.collectAsState(CommentsScreenState.Initial)

    CommentsScreenContainer(
        screenState = screenState,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CommentsScreenContainer(
    screenState: State<CommentsScreenState>,
    onBackPressed: () -> Unit,

    ) {
    val currentState = screenState.value
    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),

                    title = { Text(text = stringResource(R.string.comments)) },
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }

        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 16.dp,
                    end = 8.dp,
                    bottom = 90.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = currentState.comments, key = { it.id }) { postComment ->
                    CommentItem(comment = postComment)
                }
            }
        }
    }
}

@Composable
private fun CommentItem(
    comment: PostComment,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = comment.avatarUrl,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                           Log.d("VVV", "clicked on avatar")
                },
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = comment.authorName,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.commentText,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.publicationDate,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp
            )
        }
    }
}