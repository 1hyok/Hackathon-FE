import com.android.build.api.dsl.LibraryExtension
import com.example.hackathon.buildlogic.configureAndroidLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * 안드로이드 라이브러리 모듈 공통 플러그인.
 * com.android.library + Kotlin 적용 + 공통 SDK/Java/Kotlin 설정.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(this)
            }
        }
    }
}
