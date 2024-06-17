package com.art3mvp.newsclient.di

import android.app.Application
import android.content.Context
import com.art3mvp.newsclient.data.network.ApiFactory
import com.art3mvp.newsclient.data.network.ApiService
import com.art3mvp.newsclient.data.repository.NewsFeedRepositoryImpl
import com.art3mvp.newsclient.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository


    companion object {

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @ApplicationScope
        @Provides
        fun provideVkStorage(
            context: Context,
        ): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }
    }

}