package com.apps.pixabayapp.data.api

import com.apps.pixabayapp.data.model.PhotoDataModel

interface ApiHelper {
    suspend fun fetchPhotos(query: String, page: Int): PhotoDataModel
}