package com.apps.pixabayapp.data.api

import com.apps.pixabayapp.data.model.PhotoDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/api")
    suspend fun getUsers(@Query("q") query: String): PhotoDataModel
}