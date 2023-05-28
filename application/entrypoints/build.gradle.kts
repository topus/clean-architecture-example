description = "Ways to interact with the application (e.g. Rest APIs, Jobs, GUIs, etc.)"

plugins {
    id("clean-architecture.java-library-conventions")
}

dependencies {
    implementation(project(":application:core"))
    implementation(libs.bundles.rest)
    implementation(libs.bundles.log)

    testImplementation(libs.bundles.unitTests)
}