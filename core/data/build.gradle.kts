plugins {
    alias(libs.plugins.tracker.library)
    alias(libs.plugins.tracker.jvm.ktor)

}

android {
    namespace = "ge.tegeta.core.data"

}

dependencies {
    implementation(libs.timber)
    implementation(projects.core.domain)
    implementation(projects.core.database)
}