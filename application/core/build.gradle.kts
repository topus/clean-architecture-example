description = "Entities (domain objects) and uses cases that implement the business logic"

plugins {
    id("clean-architecture.java-library-conventions")
}

dependencies {
    implementation(libs.bundles.stringUtils)

    testImplementation(libs.bundles.unitTests)
}