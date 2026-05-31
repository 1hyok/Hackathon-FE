package com.example.hackathon.buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

/**
 * 컨벤션 플러그인에서 `libs` 버전 카탈로그에 접근하기 위한 헬퍼.
 * 플러그인 코드에서는 일반 accessor(libs.xxx)를 쓸 수 없어 명시적으로 카탈로그를 조회한다.
 */
internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")
