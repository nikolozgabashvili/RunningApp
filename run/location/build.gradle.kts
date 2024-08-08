plugins {
    alias(libs.plugins.tracker.library)
}

android {
    namespace = "ge.tegeta.run.location"

}

dependencies {
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.play.services.location)
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}