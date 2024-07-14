plugins {
    alias(libs.plugins.tracker.android.feature.ui)
}

android {
    namespace = "ge.tegeta.auth.presentation"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}