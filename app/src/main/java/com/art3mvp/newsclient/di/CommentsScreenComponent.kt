package com.art3mvp.newsclient.di

import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.presentation.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}