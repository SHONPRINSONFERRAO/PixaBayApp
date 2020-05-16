package com.apps.pixabayapp.data.repository

import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.api.ApiService

class PicsRepository(private val apiService: ApiService) : ApiHelper {

    override suspend fun fetchPhotos(query: String, page: Int) = apiService.getUsers(query, page)
}