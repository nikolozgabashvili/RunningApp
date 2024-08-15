plugins {
    alias(libs.plugins.tracker.library)
    alias(libs.plugins.tracker.jvm.ktor)

}

android {
    namespace = "ge.tegeta.run.network"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}