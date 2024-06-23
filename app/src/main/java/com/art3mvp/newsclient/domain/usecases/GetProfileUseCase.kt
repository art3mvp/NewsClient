package com.art3mvp.newsclient.domain.usecases

import com.art3mvp.newsclient.domain.entity.Profile
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
   private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<Profile?> {
        return repository.getProfile()
    }
}