import com.android.build.api.dsl.ApplicationExtension
import com.example.hackathon.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Compose 를 사용하는 :app 모듈 플러그인.
 * hackathon.android.application 적용을 전제로 Compose 설정만 덧붙인다.
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidCompose(extension)
        }
    }
}
