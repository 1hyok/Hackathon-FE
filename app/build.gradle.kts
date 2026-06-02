plugins {
    id("hackathon.android.application")
    id("hackathon.android.application.compose")
    id("hackathon.android.hilt")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.hackathon"

    defaultConfig {
        applicationId = "com.example.hackathon"
        versionCode = 1
        versionName = "1.0"

        // HackathonApplication / MainViewModel 의 Mock 모드 분기용
        buildConfigField("boolean", "USE_MOCK_API", "false")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    // 모듈 의존
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:combination"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:profile"))

    // AndroidX / Compose
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation.layout)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // 이미지 로딩 — Coil 네트워크 페처(OkHttp)는 런타임 ServiceLoader 로 자동 등록된다.
    // UI 레이어(designsystem/feature)가 아닌 app 진입점에서만 클래스패스에 둬 의존 전파를 차단.
    implementation(libs.coil.network.okhttp)

    // Kotlin Metadata (메타데이터 버전 호환성 해결)
    implementation(libs.kotlin.metadata.jvm)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
