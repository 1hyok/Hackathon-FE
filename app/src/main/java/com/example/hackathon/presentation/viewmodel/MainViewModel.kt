package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.BuildConfig
import com.example.hackathon.domain.repository.AuthRepository
import com.example.hackathon.data.local.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val tokenManager: TokenManager,
    ) : ViewModel() {
        // 초기 상태: null = 로딩 중, true = 로그인됨, false = 로그인 안됨
        private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
        val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

        init {
            checkAuthStatus()
        }

        private fun checkAuthStatus() {
            viewModelScope.launch {
                // Mock 모드가 해제된 경우, Mock 토큰이 있으면 클리어
                tokenManager.clearMockTokensIfNeeded(BuildConfig.USE_MOCK_API)
                
                // 토큰 확인
                val hasTokens = authRepository.hasValidTokens()
                _isLoggedIn.value = hasTokens
            }
        }
    }
