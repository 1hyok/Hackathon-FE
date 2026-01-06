package com.example.hackathon.data.service

import retrofit2.http.GET

interface HomeService {
    @GET("home")
    suspend fun getHome(): Unit
}

