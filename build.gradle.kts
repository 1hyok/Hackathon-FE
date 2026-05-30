// Top-level build file where you can add configuration options common to all sub-projects/modules.
// 모든 플러그인을 apply false 로 선언해 버전을 전 서브프로젝트(+build-logic 컨벤션 플러그인)에 공급한다.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.services) apply false
}
