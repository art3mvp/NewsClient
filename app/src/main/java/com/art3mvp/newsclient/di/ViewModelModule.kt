package com.art3mvp.newsclient.di

import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.presentation.camera.CameraViewModel
import com.art3mvp.newsclient.presentation.main.MainViewModel
import com.art3mvp.newsclient.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    @Binds
    fun bindCameraViewModel(viewModel: CameraViewModel): ViewModel


}