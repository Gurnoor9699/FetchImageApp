package com.example.myapplication

import javax.inject.Inject

class ImageRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchImages(): List<Image> {
        return apiService.getPhotos()
    }
}