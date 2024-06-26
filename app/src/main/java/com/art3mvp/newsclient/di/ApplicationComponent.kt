package com.art3mvp.newsclient.di

import android.app.Application
import android.content.Context
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.presentation.ViewModelFactory
import com.art3mvp.newsclient.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory

    fun getCommentsScreenComponentFactory(): CommentsScreenComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
           @BindsInstance context: Context
        ): ApplicationComponent
    }
}