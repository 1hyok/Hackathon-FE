package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.data.dto.request.LoginRequest
import com.example.hackathon.data.dto.request.UpdateProfileRequest
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.service.AuthService
import com.example.hackathon.data.service.RecipeService
import com.example.hackathon.data.service.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Swagger API 연동 예시 ViewModel
 *
 * 이 파일은 실제 사용 예시를 보여주는 참고용 코드입니다.
 * 실제 프로젝트에서는 Repository 패턴을 사용하는 것을 권장합니다.
 */
@HiltViewModel
class ExampleApiUsageViewModel
    @Inject
    constructor(
        private val authService: AuthService,
        private val userService: UserService,
        private val recipeService: RecipeService,
        private val tokenManager: TokenManager,
    ) : ViewModel() {

        // ========== 1. 로그인 플로우 예시 ==========
        private val _loginState = MutableStateFlow(LoginExampleState())
        val loginState: StateFlow<LoginExampleState> = _loginState.asStateFlow()

        /**
         * 로그인 → 토큰 저장 → 마이페이지 조회 플로우
         * @param nickname 닉네임 (이메일이 아닌 닉네임 사용)
         * @param password 비밀번호
         */
        fun loginAndLoadProfile(nickname: String, password: String) {
            viewModelScope.launch {
                _loginState.value = _loginState.value.copy(isLoading = true, error = null)

                try {
                    // 1. 로그인 API 호출 (nickname으로 로그인)
                    // 서버 응답: {"accessToken":"...","refreshToken":"..."} (BaseResponse 래퍼 없음)
                    val loginResponse = authService.login(LoginRequest(nickname = nickname, password = password))

                    // 2. 토큰 저장
                    tokenManager.saveTokens(
                        accessToken = loginResponse.accessToken,
                        refreshToken = loginResponse.refreshToken,
                    )

                    // 3. 마이페이지 조회 (토큰이 자동으로 헤더에 추가됨)
                    // 서버 응답: {"nickname":"...","myRecipeCount":0} (BaseResponse 래퍼 없음)
                    val profile = userService.getMyPage()
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        userProfile = profile,
                    )
                } catch (e: Exception) {
                    _loginState.value = _loginState.value.copy(
                        isLoading = false,
                        error = e.message ?: "로그인에 실패했습니다",
                    )
                }
            }
        }

        // ========== 2. 레시피 목록 조회 예시 ==========
        private val _recipeListState = MutableStateFlow(RecipeListState())
        val recipeListState: StateFlow<RecipeListState> = _recipeListState.asStateFlow()

        /**
         * 레시피 목록 조회 (홈 화면용)
         */
        fun loadRecipes(page: Int? = null, category: String? = null) {
            viewModelScope.launch {
                _recipeListState.value = _recipeListState.value.copy(isLoading = true, error = null)

                try {
                    val response = recipeService.getRecipes(page = page, category = category)

                    if (response.code == 200 && response.data != null) {
                        _recipeListState.value = _recipeListState.value.copy(
                            isLoading = false,
                            recipes = response.data,
                        )
                    } else {
                        _recipeListState.value = _recipeListState.value.copy(
                            isLoading = false,
                            error = response.message,
                        )
                    }
                } catch (e: Exception) {
                    _recipeListState.value = _recipeListState.value.copy(
                        isLoading = false,
                        error = e.message ?: "레시피 목록을 불러오는데 실패했습니다",
                    )
                }
            }
        }

        /**
         * 레시피 검색
         */
        fun searchRecipes(keyword: String) {
            viewModelScope.launch {
                _recipeListState.value = _recipeListState.value.copy(isLoading = true, error = null)

                try {
                    val response = recipeService.searchRecipes(keyword)

                    if (response.code == 200 && response.data != null) {
                        _recipeListState.value = _recipeListState.value.copy(
                            isLoading = false,
                            recipes = response.data,
                        )
                    } else {
                        _recipeListState.value = _recipeListState.value.copy(
                            isLoading = false,
                            error = response.message,
                        )
                    }
                } catch (e: Exception) {
                    _recipeListState.value = _recipeListState.value.copy(
                        isLoading = false,
                        error = e.message ?: "검색에 실패했습니다",
                    )
                }
            }
        }

        /**
         * 레시피 좋아요
         */
        fun likeRecipe(postId: Long) {
            viewModelScope.launch {
                try {
                    val response = recipeService.likeRecipe(postId)

                    if (response.isSuccessful) {
                        // 성공 시 목록 새로고침 또는 로컬 상태 업데이트
                        loadRecipes()
                    } else {
                        _recipeListState.value = _recipeListState.value.copy(
                            error = "서버 오류: ${response.code()} ${response.message()}",
                        )
                    }
                } catch (e: Exception) {
                    _recipeListState.value = _recipeListState.value.copy(
                        error = e.message ?: "좋아요에 실패했습니다",
                    )
                }
            }
        }

        // ========== 3. 마이페이지 프로필 수정 예시 ==========
        private val _profileState = MutableStateFlow(ProfileState())
        val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

        /**
         * 프로필 조회
         */
        fun loadProfile() {
            viewModelScope.launch {
                _profileState.value = _profileState.value.copy(isLoading = true, error = null)

                try {
                    // 서버 응답: {"nickname":"...","myRecipeCount":0} (BaseResponse 래퍼 없음)
                    val profile = userService.getMyPage()
                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        profile = profile,
                    )
                } catch (e: Exception) {
                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        error = e.message ?: "프로필을 불러오는데 실패했습니다",
                    )
                }
            }
        }

        /**
         * 프로필 수정
         */
        fun updateProfile(nickname: String, profileImageUrl: String? = null) {
            viewModelScope.launch {
                _profileState.value = _profileState.value.copy(isLoading = true, error = null)

                try {
                    val request = UpdateProfileRequest(nickname = nickname, profileImageUrl = profileImageUrl)
                    val response = userService.updateMyPage(request)

                    if (response.code == 200 && response.data != null) {
                        _profileState.value = _profileState.value.copy(
                            isLoading = false,
                            profile = response.data,
                        )
                    } else {
                        _profileState.value = _profileState.value.copy(
                            isLoading = false,
                            error = response.message,
                        )
                    }
                } catch (e: Exception) {
                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        error = e.message ?: "프로필 수정에 실패했습니다",
                    )
                }
            }
        }

        /**
         * 내가 작성한 레시피 목록 조회
         */
        fun loadMyRecipes() {
            viewModelScope.launch {
                _profileState.value = _profileState.value.copy(isLoading = true, error = null)

                try {
                    val response = userService.getMyRecipes()

                    if (response.code == 200 && response.data != null) {
                        _profileState.value = _profileState.value.copy(
                            isLoading = false,
                            myRecipes = response.data,
                        )
                    } else {
                        _profileState.value = _profileState.value.copy(
                            isLoading = false,
                            error = response.message,
                        )
                    }
                } catch (e: Exception) {
                    _profileState.value = _profileState.value.copy(
                        isLoading = false,
                        error = e.message ?: "레시피 목록을 불러오는데 실패했습니다",
                    )
                }
            }
        }
    }

// ========== UI State 데이터 클래스 ==========

data class LoginExampleState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
    val userProfile: com.example.hackathon.data.dto.response.UserProfileResponse? = null,
)

data class RecipeListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipes: List<com.example.hackathon.data.dto.response.PostPreviewDto> = emptyList(),
)

data class ProfileState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val profile: com.example.hackathon.data.dto.response.UserProfileResponse? = null,
    val myRecipes: List<com.example.hackathon.data.dto.response.MyPageRecipeResponse> = emptyList(),
)
