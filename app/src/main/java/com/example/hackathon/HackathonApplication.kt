package com.example.hackathon

import android.app.Application
import com.example.hackathon.BuildConfig
import com.example.hackathon.data.local.DummyData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HackathonApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 앱 시작 시 currentUser 초기화
        // Mock 모드가 아닐 때는 null로 시작 (로그인 안 됨)
        if (!BuildConfig.USE_MOCK_API) {
            DummyData.currentUser = null
        }
        // Mock 모드일 때도 명시적으로 null로 시작 (실제 로그인 시에만 설정)
        // DummyData.currentUser = null
    }
}
