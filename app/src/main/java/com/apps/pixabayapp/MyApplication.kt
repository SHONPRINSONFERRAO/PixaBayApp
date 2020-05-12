package com.apps.pixabayapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
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

        fun hasNetwork(): Boolean {
            return instance!!.isNetworkConnected
        }
    }
}