package com.art3mvp.newsclient.presentation.profile

import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.domain.entity.Profile
import com.art3mvp.newsclient.domain.entity.ProfileState
import com.art3mvp.newsclient.domain.usecases.GetProfileUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {

    private val profileStateFlow: StateFlow<Profile?> = getProfileUseCase()

    val profile = profileStateFlow
        .map {
            if (it != null) ProfileState.Success(it)
            else ProfileState.Loading
        }
}