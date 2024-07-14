plugins {
    alias(libs.plugins.tracker.library)
    alias(libs.plugins.tracker.android.room)
}

android {
    namespace = "ge.tegeta.core.database"
}

dependencies {

    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}