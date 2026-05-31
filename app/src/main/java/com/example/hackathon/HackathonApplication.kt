package com.example.hackathon

import android.app.Application
import com.example.hackathon.BuildConfig
import com.example.hackathon.core.model.DummyData
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HackathonApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 앱 시작 시 currentUser 초기화: Mock 모드가 아닐 때만 null(비로그인)로 시작.
        //
        // USE_MOCK_API 는 app/build.gradle.kts 의 buildConfigField 로 주입되는 컴파일 상수다.
        // 지금은 false 로 고정돼 있어 IDE 가 "!USE_MOCK_API 는 항상 true" 경고를 띄우지만,
        // 이는 단일 빌드의 상수값만 보고 내린 판단이다. Mock/실 API 를 전환하는 빌드 플래그라
        // 값을 true 로 바꾸면 분기도 갈린다 → 의도된 동작이므로 경고는 무시 가능.
        if (!BuildConfig.USE_MOCK_API) {
            DummyData.currentUser = null
        }
        // Mock 모드일 때는 DummyData.currentUser 의 기본값을 그대로 둔다(명시 설정 안 함).
    }
}
