package com.art3mvp.newsclient.presentation.profile

import com.art3mvp.newsclient.domain.entity.Profile

sealed class ProfileState {

    object Initial: ProfileState()

    data class Success(
        val profile: Profile
    ): ProfileState()
}