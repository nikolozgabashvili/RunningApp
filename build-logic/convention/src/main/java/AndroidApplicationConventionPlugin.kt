import com.android.build.api.dsl.ApplicationExtension
import ge.tegeta.convention.ApplicationConfig
import ge.tegeta.convention.ExtensionType
import ge.tegeta.convention.configureBuildTypes
import ge.tegeta.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")

            }
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = ApplicationConfig.APPLICATION_ID
                    targetSdk = ApplicationConfig.TARGET_SDK
                    versionCode = ApplicationConfig.VERSION_CODE
                    versionName = ApplicationConfig.VERSION_NAME

                }
                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.APPLICATION)
            }
        }
    }
}