description = "Acceptance Tests for the BDD cycle (objective: have conversaions and demonstrate requirements)"

plugins {
    id("clean-architecture.java-application-conventions")
}

test {

    // make gradle print the test result for each test in the build (we like to see the acceptance tests running)
    testLogging {
        events "passed", "skipped", "failed"
    }

    // set the output folder for the acceptance tests that use yatspec, and print the full path after the test execution
    systemProperty "yatspec.output.dir", "build/reports/yatspec"
    doLast {
        println "==========================================================================================="
        println "Acceptance tests output: ${project.buildDir.absolutePath}/reports/yatspec/com/clean/example"
        println "==========================================================================================="
    }
}

// we want to run the acceptance tests after the unit tests, to follow the testing pyramid
test.mustRunAfter(
        ":application:configuration:test",
        ":application:core:test",
        ":application:dataproviders:test",
        ":application:entrypoints:test"
)

// we must copy the h2-schema.sql script into the resources folder of this module in order for the tests
// to be able to create the in-memory database successfull when initialising the connection
task copyTestResources(type: Copy) {
    println "Copying resource"
    from "${project(':application:dataproviders').buildDir}/resources/main/h2-schema.sql"
    into "${buildDir}/resources/test"
}
processTestResources.dependsOn copyTestResources

dependencies {
    implementation(project(":application:configuration"))
    implementation(libs.unit_tests)
    implementation(libs.acceptance_tests)
    implementation(libs.end_to_end_test)

    testImplementation(libs.string_utils)
}