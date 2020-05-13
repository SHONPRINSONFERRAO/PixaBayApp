package com.apps.pixabayapp.data.model

data class PhotoDataModel(
    val hits: ArrayList<ListOfPhotos> = arrayListOf()
)

data class ListOfPhotos(
    val id: Int = 0,
    val pageURL: String = "",
    val tags: String = "",
    val largeImageURL: String = "",
    val fullHDURL: String = "",
    val previewURL: String = "",
    val views: String = "",
    val webformatURL: String = "",
    val downloads: String = "",
    val favorites: String = "",
    val likes: String = "",
    val comments: String = ""
)
