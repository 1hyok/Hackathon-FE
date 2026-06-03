plugins {
    id("hackathon.android.library")
    id("hackathon.android.library.compose")
}

android {
    namespace = "com.example.hackathon.core.designsystem"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.coil.compose)
}
