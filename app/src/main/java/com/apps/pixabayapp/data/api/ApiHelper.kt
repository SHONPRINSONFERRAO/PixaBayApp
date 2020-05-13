package com.apps.pixabayapp.data.api

import com.apps.pixabayapp.data.model.PhotoDataModel

class ApiHelper(private val apiService: ApiService) {

    suspend fun getUsers(query: String, page: Int): PhotoDataModel =
        apiService.getUsers(query, page)
}