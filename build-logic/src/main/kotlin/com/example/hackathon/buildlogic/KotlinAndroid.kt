package com.example.hackathon.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

/**
 * Android Application/Library 모듈 공통 SDK·Java·Kotlin 설정.
 * AGP 9 에서 CommonExtension 의 제네릭/블록 메서드가 제거돼 concrete 타입(Application/Library)별로 설정한다.
 * compileSdk 36 / minSdk 36 / JVM 17 을 단일 지점에서 강제.
 */
internal fun Project.configureAndroidApplication(extension: ApplicationExtension) {
    extension.apply {
        compileSdk { version = release(36) }
        defaultConfig {
            // AGP 9 부터 minSdk/targetSdk 의 Int setter 는 컨벤션 플러그인에서 신뢰성 있게 적용되지 않음.
            // 블록 형식 minSdk { version = release(N) } 사용 필수.
            minSdk { version = release(36) }
            targetSdk { version = release(36) }
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureKotlin()
}

internal fun Project.configureAndroidLibrary(extension: LibraryExtension) {
    extension.apply {
        compileSdk { version = release(36) }
        defaultConfig {
            // AGP 9: 블록 형식 minSdk 사용 (Int setter 는 컨벤션 플러그인에서 무효).
            minSdk { version = release(36) }
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    configureKotlin()
}

/** Kotlin JVM 타깃을 17 로 통일. */
private fun Project.configureKotlin() {
    extensions.configure(KotlinAndroidProjectExtension::class.java) {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}
