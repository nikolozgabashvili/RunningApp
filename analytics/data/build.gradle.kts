plugins {
    alias(libs.plugins.tracker.library)
    alias(libs.plugins.tracker.android.room)
}

android {
    namespace = "ge.tegeta.analytics.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.koin)

    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.domain)
    implementation(projects.analytics.domain)
}