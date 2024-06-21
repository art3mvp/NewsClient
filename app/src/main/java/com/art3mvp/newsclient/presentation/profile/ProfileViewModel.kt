package com.art3mvp.newsclient.presentation.profile

import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.entity.Profile
import com.art3mvp.newsclient.domain.usecases.GetProfileUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {

    val profile: StateFlow<ProfileState> = getProfileUseCase()
}