package com.art3mvp.newsclient.di

import androidx.lifecycle.ViewModel
import com.art3mvp.newsclient.presentation.comments.CommentsViewModel
import com.art3mvp.newsclient.presentation.main.MainViewModel
import com.art3mvp.newsclient.presentation.news.NewsFeedViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


}