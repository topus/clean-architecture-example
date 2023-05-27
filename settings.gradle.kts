plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "clean-architecture-example"

// application
include("application:core")
//include("application:dataproviders")
//include("application:entrypoints")
//include("application:configuration")

// testing
//include("acceptance-tests")
//include("integration-tests")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            //val springVersion by extra("4.2.5.RELEASE")
            //val springbootVersion by extra("1.3.3.RELEASE")

            library("commons-lang3", "org.apache.commons:commons-lang3:3.4")

            library("mockito-core", "org.mockito:mockito-core:5.3.1")
            library("assertj-core", "org.assertj:assertj-core:3.24.2")

            bundle("stringutils", listOf("commons-lang3"))
            bundle("unittests", listOf("mockito-core", "assertj-core"))
        }
    }
}

