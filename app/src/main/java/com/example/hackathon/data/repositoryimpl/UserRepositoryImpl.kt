package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.BuildConfig
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.mapper.toEntity
import com.example.hackathon.data.service.UserService
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val userService: UserService,
        private val tokenManager: TokenManager,
    ) : UserRepository {
        override suspend fun getProfile(): Result<User> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 더미 데이터 반환
                    val currentUser = DummyData.currentUser
                        ?: return Result.failure(Exception("User not logged in"))
                    Result.success(currentUser)
                } else {
                    // 실제 API 호출
                    // 서버 응답: {"nickname":"...","myRecipeCount":0} (BaseResponse 래퍼 없음)
                    val profile = userService.getMyPage()
                    
                    // userId는 JWT에서 가져오거나 기본값 사용
                    val userId = tokenManager.getUserIdFromToken()?.toString()
                        ?: profile.id
                        ?: "unknown"
                    
                    val user = User(
                        id = userId,
                        nickname = profile.nickname,
                        profileImageUrl = profile.profileImageUrl,
                    )
                    
                    // 로컬 더미 데이터도 업데이트 (하위 호환성)
                    DummyData.currentUser = user
                    
                    Result.success(user)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun updateProfile(
            nickname: String,
            profileImageUrl: String?,
        ): Result<User> {
            return try {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 로컬 사용자 정보 업데이트
                    val currentUser = DummyData.currentUser
                        ?: return Result.failure(Exception("User not logged in"))
                    val updatedUser =
                        currentUser.copy(
                            nickname = nickname,
                            profileImageUrl = profileImageUrl,
                        )
                    DummyData.currentUser = updatedUser
                    Result.success(updatedUser)
                } else {
                    // 실제 API 호출
                    val request = com.example.hackathon.data.dto.request.UpdateProfileRequest(
                        nickname = nickname,
                        profileImageUrl = profileImageUrl,
                    )
                    val response = userService.updateMyPage(request)
                    
                    // BaseResponse에서 data 추출
                    val profile = response.data
                        ?: return Result.failure(Exception(response.message ?: "프로필 업데이트 실패"))
                    
                    // userId는 JWT에서 가져오거나 기본값 사용
                    val userId = tokenManager.getUserIdFromToken()?.toString()
                        ?: profile.id
                        ?: "unknown"
                    
                    val updatedUser = User(
                        id = userId,
                        nickname = profile.nickname,
                        profileImageUrl = profile.profileImageUrl,
                    )
                    
                    // 로컬 더미 데이터도 업데이트 (하위 호환성)
                    DummyData.currentUser = updatedUser
                    
                    Result.success(updatedUser)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
