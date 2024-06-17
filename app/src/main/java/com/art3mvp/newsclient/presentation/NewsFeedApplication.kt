package com.art3mvp.newsclient.presentation

import android.app.Application
import com.art3mvp.newsclient.di.ApplicationComponent
import com.art3mvp.newsclient.di.DaggerApplicationComponent
import com.art3mvp.newsclient.domain.entity.FeedPost
import com.art3mvp.newsclient.domain.entity.StatisticItem
import com.art3mvp.newsclient.domain.entity.StatisticType

class NewsFeedApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}