plugins {
    alias(libs.plugins.tracker.library.compose)
}

android {
    namespace = "ge.tegeta.core.designsystem"

}

dependencies {
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    api(libs.androidx.material3)
}