import com.example.hackathon.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * 피처(:feature:*) 모듈 공통 플러그인.
 * library + compose + hilt 를 묶고, 모든 피처가 공유하는 core 모듈(domain/model/designsystem)과
 * Compose/Navigation/Hilt-Navigation/Lifecycle/Coil 의존성을 일괄 주입한다.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("hackathon.android.library")
                apply("hackathon.android.library.compose")
                apply("hackathon.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:designsystem"))

                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())
                add(
                    "implementation",
                    libs.findLibrary("androidx-compose-material-icons-extended").get()
                )
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())
                add("implementation", libs.findLibrary("hilt-navigation-compose").get())
                add("implementation", libs.findLibrary("coil-compose").get())
                add("implementation", libs.findLibrary("coil-network-okhttp").get())

                add("testImplementation", libs.findLibrary("junit").get())
                add("testImplementation", libs.findLibrary("mockk").get())
                add("testImplementation", libs.findLibrary("turbine").get())
                add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            }
        }
    }
}
