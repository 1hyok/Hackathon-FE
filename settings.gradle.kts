pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Hackathon"
include(":app")

// core 모듈
include(":core:model")
include(":core:designsystem")
include(":core:datastore")
include(":core:network")
include(":core:domain")
include(":core:data")

// feature 모듈
include(":feature:auth")
include(":feature:home")
include(":feature:combination")
include(":feature:onboarding")
include(":feature:profile")
