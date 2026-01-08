package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.data.local.DummyData
import com.example.hackathon.data.service.UserService
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userService: UserService,
    ) : UserRepository {
        override suspend fun getProfile(): Result<User> {
            return try {
                delay(300)
                // TODO: 실제 API 호출
                // val response = userService.getProfile()
                // if (response.isSuccess) {
                //     Result.success(response.data.toEntity())
                // } else {
                //     Result.failure(Exception(response.message))
                // }

                // 임시로 더미 데이터 반환
                Result.success(DummyData.currentUser)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun updateProfile(
            nickname: String,
            profileImageUrl: String?,
        ): Result<User> {
            return try {
                delay(500)
                // TODO: 실제 API 호출
                // val request = UpdateProfileRequest(nickname, profileImageUrl)
                // val response = userService.updateProfile(request)
                // if (response.isSuccess) {
                //     val updatedUser = response.data.toEntity()
                //     DummyData.currentUser = updatedUser
                //     Result.success(updatedUser)
                // } else {
                //     Result.failure(Exception(response.message))
                // }

                // 임시로 로컬 사용자 정보 업데이트
                val updatedUser =
                    DummyData.currentUser.copy(
                        nickname = nickname,
                        profileImageUrl = profileImageUrl,
                    )
                DummyData.currentUser = updatedUser
                Result.success(updatedUser)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
