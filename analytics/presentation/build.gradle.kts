plugins {
    alias(libs.plugins.tracker.android.feature.ui)
}

android {
    namespace = "ge.tegeta.analytics.presentation"
}

dependencies {

    implementation(projects.analytics.domain)
}