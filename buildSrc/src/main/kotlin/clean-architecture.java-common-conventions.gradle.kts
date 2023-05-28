/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the java Plugin to add support for Java.
    java
}

java.sourceCompatibility = JavaVersion.VERSION_19

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.10.0")
    }

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.withType<Test> {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
