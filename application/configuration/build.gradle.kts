description = "Wires the application together."

plugins {
    id("clean-architecture.java-application-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

extra["springCloudVersion"] = "2022.0.3"

// configure spring boot with gradle: start
/*
buildscript {

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springboot_version}")
    }
}
apply plugin: 'spring-boot'
*/
// configure spring boot with gradle: end

// generate the main application jar in the "application" folder, to make it easier to find
/*
jar {
    archiveName = "clean-architecture-example.jar"
    destinationDir = project(":application").getBuildDir()
}
*/

dependencies {
    implementation(project(":application:core"))
    implementation(project(":application:dataproviders"))
    implementation(project(":application:entrypoints"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    implementation(libs.bundles.rest)
    implementation(libs.bundles.log)

    testImplementation(libs.bundles.unitTests)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}