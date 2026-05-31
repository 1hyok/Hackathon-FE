package com.example.hackathon.core.network.di

import com.example.hackathon.core.network.service.AuthService
import com.example.hackathon.core.network.service.CombinationService
import com.example.hackathon.core.network.service.HomeService
import com.example.hackathon.core.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun providesHomeService(@MainRetrofit retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesCombinationService(@MainRetrofit retrofit: Retrofit): CombinationService =
        retrofit.create(CombinationService::class.java)

    @Provides
    @Singleton
    fun providesUserService(@MainRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesAuthService(@AuthRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
