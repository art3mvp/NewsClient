package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import com.art3mvp.newsclient.presentation.profile.ProfileState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
   private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<ProfileState> {
        return repository.getProfile()
    }
}