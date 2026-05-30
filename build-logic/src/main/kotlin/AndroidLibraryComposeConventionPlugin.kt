import com.android.build.api.dsl.LibraryExtension
import com.example.hackathon.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Compose 를 사용하는 안드로이드 라이브러리 모듈 플러그인.
 * hackathon.android.library 적용을 전제로 Compose 설정만 덧붙인다.
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("hackathon.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val extension = extensions.getByType(LibraryExtension::class.java)
            configureAndroidCompose(extension)
        }
    }
}
