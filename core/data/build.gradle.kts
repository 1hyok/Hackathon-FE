plugins {
    id("hackathon.android.library")
    id("hackathon.android.hilt")
}

android {
    namespace = "com.example.hackathon.core.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        // repositoryimpl 의 Mock/실 API 분기용 (BuildConfig.USE_MOCK_API)
        buildConfigField("boolean", "USE_MOCK_API", "false")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))

    implementation(libs.retrofit)
}
