package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.UpdateProfileRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.MyPageRecipeResponse
import com.example.hackathon.data.dto.response.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserService {
    // 내 프로필 조회
    // 서버 응답: {"nickname":"...","myRecipeCount":0} (BaseResponse 래퍼 없음)
    @GET("users/mypage")
    suspend fun getMyPage(): UserProfileResponse

    // 내 프로필 수정
    @PATCH("users/mypage")
    suspend fun updateMyPage(
        @Body request: UpdateProfileRequest,
    ): BaseResponse<UserProfileResponse>

    // 내가 작성한 레시피 목록
    @GET("users/mypage/recipes")
    suspend fun getMyRecipes(): BaseResponse<List<MyPageRecipeResponse>>

    // 내가 좋아요한 레시피 목록
    @GET("users/mypage/likes")
    suspend fun getMyLikedRecipes(): BaseResponse<List<MyPageRecipeResponse>>
}
