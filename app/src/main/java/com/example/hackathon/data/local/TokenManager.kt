package com.example.hackathon.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_preferences")

@Singleton
class TokenManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        companion object {
            private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
            private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        }

        val accessToken: Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[ACCESS_TOKEN_KEY]
            }

        val refreshToken: Flow<String?> =
            context.dataStore.data.map { preferences ->
                preferences[REFRESH_TOKEN_KEY]
            }

        suspend fun saveTokens(accessToken: String, refreshToken: String) {
            context.dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN_KEY] = accessToken
                preferences[REFRESH_TOKEN_KEY] = refreshToken
            }
        }

        suspend fun getAccessToken(): String? {
            return context.dataStore.data.map { it[ACCESS_TOKEN_KEY] }.first()
        }

        suspend fun getRefreshToken(): String? {
            return context.dataStore.data.map { it[REFRESH_TOKEN_KEY] }.first()
        }

        suspend fun clearTokens() {
            context.dataStore.edit { preferences ->
                preferences.remove(ACCESS_TOKEN_KEY)
                preferences.remove(REFRESH_TOKEN_KEY)
            }
        }

        suspend fun hasTokens(): Boolean {
            val accessToken = getAccessToken()
            val refreshToken = getRefreshToken()
            return !accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()
        }

        /**
         * Mock 모드가 해제된 경우, Mock 토큰이 있으면 클리어
         * 앱 시작 시 호출하여 이전 Mock 토큰을 제거
         */
        suspend fun clearMockTokensIfNeeded(useMockApi: Boolean) {
            if (!useMockApi) {
                val accessToken = getAccessToken()
                // Mock 토큰인지 확인 (mock_access_token으로 시작)
                if (accessToken != null && accessToken.startsWith("mock_")) {
                    clearTokens()
                }
            }
        }

        /**
         * JWT 토큰에서 userId 추출
         * JWT payload의 "sub" 필드에서 userId를 가져옴
         */
        suspend fun getUserIdFromToken(): Long? {
            val accessToken = getAccessToken() ?: return null
            return try {
                // JWT는 header.payload.signature 형식
                val parts = accessToken.split(".")
                if (parts.size != 3) return null
                
                // payload 부분 디코딩
                val payload = parts[1]
                // Base64 URL-safe 디코딩
                // 패딩이 없을 수 있으므로 추가
                val paddedPayload = when (payload.length % 4) {
                    0 -> payload
                    2 -> "$payload=="
                    3 -> "$payload="
                    else -> payload
                }
                
                val decodedBytes = android.util.Base64.decode(
                    paddedPayload,
                    android.util.Base64.URL_SAFE
                )
                val payloadJson = String(decodedBytes, Charsets.UTF_8)
                
                // JSON 파싱
                val jsonObject = org.json.JSONObject(payloadJson)
                val sub = jsonObject.optString("sub", null)
                sub?.toLongOrNull()
            } catch (e: Exception) {
                null
            }
        }
    }
