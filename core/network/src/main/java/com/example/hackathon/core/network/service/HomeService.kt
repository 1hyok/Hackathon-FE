package com.example.hackathon.core.network.service

import retrofit2.http.GET

interface HomeService {
    @GET("home")
    suspend fun getHome()
}
