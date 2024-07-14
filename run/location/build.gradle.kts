plugins {
    alias(libs.plugins.tracker.library)
}

android {
    namespace = "ge.tegeta.run.location"

}

dependencies {
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    //implementation(libs.google.android.gms.play.services.location)

    implementation(projects.core.domain)
    implementation(projects.run.domain)
}