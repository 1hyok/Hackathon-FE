plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
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

        buildConfigField("String", "BASE_URL", "\"https://api.example.com/\"")
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
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

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

// Ktlint 설정
ktlint {
    android.set(true)
    ignoreFailures.set(false) // 플러그인 12.1.1로 업그레이드하여 Java 21 호환성 문제 해결
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
        exclude("**/*.gradle.kts") // Kotlin 스크립트 파일 제외
    }
}

// 코드 품질 검사 통합 Task
tasks.register("codeQualityCheck") {
    group = "verification"
    description = "Runs all code quality checks (Ktlint + Detekt + Tests)"

    dependsOn("ktlintCheck", "detekt", "test")

    doLast {
        println("✅ All code quality checks completed!")
    }
}

// 빌드 전 자동으로 코드 포맷팅 실행 (검사는 Git Hook에서 처리)
// 참고: 빌드 시마다 검사를 실행하면 빌드가 느려질 수 있으므로,
//       포맷팅만 자동 실행하고 검사는 Git Hook 또는 수동 실행으로 처리
// 주의: ktlintFormat이 실패하면 빌드가 실패합니다. 코드 작성 시 KtLint 규칙을 준수해야 합니다.
tasks.named("preBuild") {
    dependsOn("ktlintFormat")
}
