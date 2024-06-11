package com.art3mvp.newsclient.presentation.main

sealed class AuthState {

    object Initial: AuthState()

    object Authorized: AuthState()

    object NotAuthorized: AuthState()

}