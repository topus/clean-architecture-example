plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "clean-architecture-example"

// application
include("application:core")
include("application:dataproviders")
include("application:entrypoints")
//include("application:configuration")

// testing
//include("acceptance-tests")
//include("integration-tests")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            //val springbootVersion by extra("1.3.3.RELEASE")
            version("springVersion","6.0.9")

            library("commons-lang3", "org.apache.commons:commons-lang3:3.4")

            library("spring-jdbc", "org.springframework","spring-jdbc").versionRef("springVersion")
            library("spring-web", "org.springframework","spring-web").versionRef("springVersion")

            library("slf4j-api", "org.slf4j:slf4j-api:2.0.7")

            library("mockito-core", "org.mockito:mockito-core:5.3.1")
            library("assertj-core", "org.assertj:assertj-core:3.24.2")

            bundle("stringUtils", listOf("commons-lang3"))
            bundle("log", listOf("slf4j-api"))
            bundle("database", listOf("spring-jdbc"))
            bundle("rest", listOf("spring-web"))
            bundle("unitTests", listOf("mockito-core", "assertj-core"))
        }
    }
}

