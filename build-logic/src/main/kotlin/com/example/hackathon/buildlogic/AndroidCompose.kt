package com.example.hackathon.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Jetpack Compose 공통 설정.
 * AGP 9 에서 buildFeatures 가 concrete 타입으로 이동해 Application/Library 별로 활성화한다.
 * org.jetbrains.kotlin.plugin.compose 플러그인은 각 Compose 모듈에서 별도 적용.
 */
internal fun Project.configureComposeApplication(extension: ApplicationExtension) {
    extension.buildFeatures.compose = true
    addComposeDependencies()
}

internal fun Project.configureComposeLibrary(extension: LibraryExtension) {
    extension.buildFeatures.compose = true
    addComposeDependencies()
}

/** Compose BOM 기반 공통 의존성 주입. */
private fun Project.addComposeDependencies() {
    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))

        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())

        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
    }
}
