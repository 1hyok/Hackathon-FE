package com.example.hackathon.core.data.di

import com.example.hackathon.core.data.repositoryimpl.AuthRepositoryImpl
import com.example.hackathon.core.data.repositoryimpl.CombinationRepositoryImpl
import com.example.hackathon.core.data.repositoryimpl.RecipeRepositoryImpl
import com.example.hackathon.core.data.repositoryimpl.UserRepositoryImpl
import com.example.hackathon.core.domain.repository.AuthRepository
import com.example.hackathon.core.domain.repository.CombinationRepository
import com.example.hackathon.core.domain.repository.RecipeRepository
import com.example.hackathon.core.domain.repository.UserRepository
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

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindRecipeRepository(recipeRepositoryImpl: RecipeRepositoryImpl): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}
