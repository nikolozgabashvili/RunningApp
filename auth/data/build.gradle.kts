plugins {
    alias(libs.plugins.tracker.library)
    alias(libs.plugins.tracker.jvm.ktor)
}

android {
    namespace = "ge.tegeta.auth.data"
}

dependencies {

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}