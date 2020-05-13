package com.apps.pixabayapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.apps.pixabayapp.di.component.ApiComponent
import com.apps.pixabayapp.di.component.DaggerApiComponent
import com.apps.pixabayapp.di.module.ApiModule
import com.apps.pixabayapp.di.module.AppModule


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        mApiComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this))
            .apiModule(ApiModule)
            .build();
    }


    fun getNetComponent(): ApiComponent {
        return mApiComponent
    }


    fun getInstance(): MyApplication? {
        return instance
    }

    private val isNetworkConnected: Boolean
        private get() {
            val cm =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetwork = cm!!.activeNetworkInfo
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting
        }

    companion object {
        var instance: MyApplication? = null
            private set

        private lateinit var mApiComponent: ApiComponent

        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }
    }
}