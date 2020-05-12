package com.apps.pixabayapp.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apps.pixabayapp.utils.Resource
import androidx.lifecycle.liveData
import com.apps.pixabayapp.data.repository.PicsRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel (val picsRepo: PicsRepository) : ViewModel() {
    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel destroyed!")
    }

    fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = picsRepo.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}