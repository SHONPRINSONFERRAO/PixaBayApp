package com.apps.pixabayapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.pixabayapp.MyApplication
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.apps.pixabayapp.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val apiHelper: ApiHelper) : ViewModel() {
    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel destroyed!")
    }

    private val photos = MutableLiveData<Resource<PhotoDataModel>>()

    fun fetchPhotos(query: String, page: Int) = viewModelScope.launch {
        photos.postValue(Resource.loading(data = null))
        try {
            photos.postValue(Resource.success(data = apiHelper.fetchPhotos(query, page)))
        } catch (exception: Exception) {
            if (!MyApplication.hasNetwork()) {
                photos.postValue(Resource.noContentFound(data = null, message = "No Content Found"))
            } else {
                photos.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
    }

    fun getUsers(): LiveData<Resource<PhotoDataModel>> = photos


}