import com.android.build.api.dsl.ApplicationExtension
import ge.tegeta.convention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("ge.tracker.application")
            val extension = extensions.getByType<ApplicationExtension>()
            configureCompose(extension)
        }
    }
}