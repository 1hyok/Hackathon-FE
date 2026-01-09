package com.example.hackathon.di

import com.example.hackathon.BuildConfig
import com.example.hackathon.data.network.AuthInterceptor
import com.example.hackathon.data.service.RecipeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesOkHttpClient(
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory {
        val json =
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = false
            }
        return json.asConverterFactory("application/json".toMediaType())
    }

    @MainRetrofit
    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(converterFactory)
            .build()

    // 토큰 재발급용 별도 Retrofit (AuthInterceptor 없이)
    @AuthRetrofit
    @Provides
    @Singleton
    fun providesAuthRetrofit(
        converterFactory: Converter.Factory,
    ): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val authClient =
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(authClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Provides
    fun provideRecipeService(@MainRetrofit retrofit: Retrofit): RecipeService = retrofit.create(RecipeService::class.java)
}
