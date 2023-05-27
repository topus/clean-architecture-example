description = "Retrieve data from whatever source (db, 3rd party, etc.)"

plugins {
    id("clean-architecture.java-library-conventions")
}

dependencies {
    implementation(project(":application:core"))

    implementation(libs.bundles.database)
    testImplementation(libs.bundles.unitTests)
}
