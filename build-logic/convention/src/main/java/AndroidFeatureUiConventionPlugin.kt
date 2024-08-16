import ge.tegeta.convention.addUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

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