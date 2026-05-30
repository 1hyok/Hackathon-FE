plugins {
    id("hackathon.android.library")
    id("hackathon.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.hackathon.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("boolean", "USE_MOCK_API", "false")
        buildConfigField("String", "BASE_URL", "\"http://13.125.27.133/\"")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
}
