package com.apps.pixabayapp.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.repository.PicsRepository
import com.apps.pixabayapp.ui.home.viewmodel.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(PicsRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}