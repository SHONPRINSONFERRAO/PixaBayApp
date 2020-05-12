package com.apps.pixabayapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.apps.pixabayapp.MyApplication
import com.apps.pixabayapp.data.repository.PicsRepository
import com.apps.pixabayapp.utils.Resource
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val picsRepo: PicsRepository) : ViewModel() {
    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel destroyed!")
    }

    fun getUsers(query: String, page: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = picsRepo.getUsers(query, page)))
        } catch (exception: Exception) {
            if (!MyApplication.hasNetwork()) {
                emit(Resource.noContentFound(data = null, message = "No Content Found" ))
            } else {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }
}