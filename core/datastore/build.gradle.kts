plugins {
    id("hackathon.android.library")
    id("hackathon.android.hilt")
}

android {
    namespace = "com.example.hackathon.core.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
