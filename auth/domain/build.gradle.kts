plugins{
    alias(libs.plugins.tracker.jvm.library)
}
dependencies{
    implementation(projects.core.domain)
}