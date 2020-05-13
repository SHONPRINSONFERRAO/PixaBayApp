package com.apps.pixabayapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal class AppModule(mApplication: Application) {
    private val mApplication: Application

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return mApplication
    }

    init {
        this.mApplication = mApplication
    }
}