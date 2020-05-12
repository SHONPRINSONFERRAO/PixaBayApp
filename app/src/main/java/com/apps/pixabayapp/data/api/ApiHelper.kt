package com.apps.pixabayapp.data.api

import com.apps.pixabayapp.data.model.PhotoDataModel

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers(query: String): PhotoDataModel = apiService.getUsers(query)
}