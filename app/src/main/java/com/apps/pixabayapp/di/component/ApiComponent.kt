package com.apps.pixabayapp.di.component

import com.apps.pixabayapp.di.module.ApiModule
import com.apps.pixabayapp.di.module.AppModule
import com.apps.pixabayapp.ui.home.view.HomeActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, ApiModule::class])
interface ApiComponent {
    fun inject(activity: HomeActivity?)
}