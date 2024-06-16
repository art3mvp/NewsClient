package com.art3mvp.newsclient.domain

sealed class AuthState {

    object Initial: AuthState()

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

}