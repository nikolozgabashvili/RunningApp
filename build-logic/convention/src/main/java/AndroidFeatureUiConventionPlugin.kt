import com.android.build.api.dsl.LibraryExtension
import ge.tegeta.convention.addUiLayerDependencies
import ge.tegeta.convention.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureUiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("ge.tracker.library.compose")
            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}