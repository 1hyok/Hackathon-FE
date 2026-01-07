package com.example.hackathon.di

import com.example.hackathon.data.repositoryimpl.CombinationRepositoryImpl
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCombinationRepository(combinationRepositoryImpl: CombinationRepositoryImpl): CombinationRepository
}
