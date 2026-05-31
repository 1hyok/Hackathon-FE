import com.android.build.api.dsl.ApplicationExtension
import com.example.hackathon.buildlogic.configureAndroidApplication
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * 안드로이드 애플리케이션(:app) 모듈 공통 플러그인.
 * com.android.application + Kotlin 적용 + 공통 SDK/Java/Kotlin 설정.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureAndroidApplication(this)
            }
        }
    }
}
