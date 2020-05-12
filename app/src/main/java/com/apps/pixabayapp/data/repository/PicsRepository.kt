package com.apps.pixabayapp.data.repository

import com.apps.pixabayapp.data.api.ApiHelper

class PicsRepository(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers("apple")
}