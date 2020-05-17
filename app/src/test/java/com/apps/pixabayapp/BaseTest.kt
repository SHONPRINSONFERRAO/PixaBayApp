package com.apps.pixabayapp

import com.apps.pixabayapp.data.model.ListOfPhotos
import com.apps.pixabayapp.data.model.PhotoDataModel


open class BaseTest {


    fun getSuccessResponse(): PhotoDataModel {
        return PhotoDataModel(
            arrayListOf(
                ListOfPhotos(
                    11,
                    "dummyUrl",
                    "tags",
                    "dummyLargeUrl",
                    "dummyHdUrl",
                    "previewUrl",
                    "200",
                    "webUrl",
                    "2000",
                    "22",
                    "2",
                    "20"
                ),
                ListOfPhotos(
                    12,
                    "dummyUrl",
                    "tags",
                    "dummyLargeUrl",
                    "dummyHdUrl",
                    "previewUrl",
                    "200",
                    "webUrl",
                    "2000",
                    "22",
                    "2",
                    "20"
                )
            )
        )

    }

    fun getEmptyResponse(): PhotoDataModel {
        return PhotoDataModel(
            arrayListOf()
        )

    }
}
