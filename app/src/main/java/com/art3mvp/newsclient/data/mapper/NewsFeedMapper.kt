package com.art3mvp.newsclient.data.mapper

import android.util.Log
import com.art3mvp.newsclient.data.model.NewsFeedResponseDto
import com.art3mvp.newsclient.domain.FeedPost
import com.art3mvp.newsclient.domain.StatisticItem
import com.art3mvp.newsclient.domain.StatisticType
import kotlin.math.absoluteValue


class NewsFeedMapper {

    fun mapResponseToPosts(responseDto: NewsFeedResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            groups.find { it.id == post.sourceId.absoluteValue }?.let {group ->
                Log.d("HTTP", group.groupPhoto)
                val feedPost = FeedPost(
                    id = post.id,
                    communityName = group.name,
                    publicationDate = post.date.toString(),
                    contentDescription = post.text,
                    communityImageUrl = group.groupPhoto,
                    contentImageUrl = post.attachments?.firstOrNull()?.photo?.urls?.lastOrNull()?.url,
                    statistics = listOf(
                        StatisticItem(StatisticType.LIKES, post.likes.count),
                        StatisticItem(StatisticType.COMMENTS, post.comments.count),
                        StatisticItem(StatisticType.SHARES, post.reposts.count),
                        StatisticItem(StatisticType.VIEWS, post.views.count),
                        )
                    )
                result.add(feedPost)
            }
        }
        return result
    }
}