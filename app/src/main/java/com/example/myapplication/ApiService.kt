package com.example.myapplication

import android.telecom.Call
import retrofit2.http.GET

interface ApiService  {
    @GET("photos")
    suspend fun getPhotos(): List<Image>
}