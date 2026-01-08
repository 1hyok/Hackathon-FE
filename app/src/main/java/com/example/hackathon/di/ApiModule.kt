package com.example.hackathon.di

import com.example.hackathon.data.service.CombinationService
import com.example.hackathon.data.service.HomeService
import com.example.hackathon.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesHomeService(retrofit: Retrofit): HomeService = retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesCombinationService(retrofit: Retrofit): CombinationService = retrofit.create(CombinationService::class.java)

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
}
