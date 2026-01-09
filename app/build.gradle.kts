plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.example.hackathon"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.hackathon"
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // 서버 준비 전까지 Mock 모드 사용 (true: Mock, false: 실제 API)
        buildConfigField("boolean", "USE_MOCK_API", "true")
        buildConfigField("String", "BASE_URL", "\"http://13.125.27.133/\"")
    }

    buildFeatures {
        compose = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17 // Detekt 호환성을 위해 17로 설정
    }
    kotlinOptions {
        jvmTarget = "17" // Java 컴파일러와 일치시킴 (compileOptions와 동일)
    }
}

dependencies {
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Network
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // Image Loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.compose.foundation)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Kotlin Metadata (메타데이터 버전 호환성 해결)
    // Kotlin 2.0+에서는 kotlin-metadata-jvm 사용 (kotlinx가 아님)
    implementation("org.jetbrains.kotlin:kotlin-metadata-jvm:2.0.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.7.0"))
    implementation("com.google.firebase:firebase-analytics")

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Detekt 설정
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/../config/detekt.yml")
    baseline = file("$projectDir/../config/detekt-baseline.xml")

    // Compose Rules는 나중에 추가 (의존성 문제 해결 후)
    // dependencies {
    //     detektPlugins(libs.compose.rules.detekt)
    // }
}


// 코드 품질 검사 통합 Task
tasks.register("codeQualityCheck") {
    group = "verification"
    description = "Runs all code quality checks (Detekt + Tests)"

    dependsOn("detekt", "test")

    doLast {
        println("✅ All code quality checks completed!")
    }
}

