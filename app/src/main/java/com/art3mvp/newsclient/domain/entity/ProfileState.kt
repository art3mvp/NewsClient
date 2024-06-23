package com.art3mvp.newsclient.domain.entity

sealed class ProfileState {

    object Initial: ProfileState()

    object Loading : ProfileState()

    data class Success(
        val profile: Profile
    ): ProfileState()
}