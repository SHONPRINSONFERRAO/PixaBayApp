package com.apps.pixabayapp.ui.details.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.apps.pixabayapp.data.repository.PicsRepository

class DetailsViewModel(private val picsRepo: PicsRepository) : ViewModel() {
    init {
        Log.i("DetailsViewModel", "DetailsViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("DetailsViewModel", "DetailsViewModel destroyed!")
    }
}