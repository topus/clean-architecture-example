description = "Clean Architecture Example"

plugins {
    id("org.springframework.boot") version "3.1.0" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

group = "be.topus"
version = "1.0-SNAPSHOT"

subprojects {

/*
    ext.libs = [
            acceptance_tests       : [
                    "com.googlecode.yatspec:yatspec:1.20"
            ],

            integration_test       : [
                    "org.springframework:spring-test:${spring_version}",
                    "org.skyscreamer:jsonassert:1.3.0",
                    "com.cedarsoftware:json-io:4.4.0"
            ],

            end_to_end_test        : [
                    "org.springframework:spring-test:${spring_version}",
                    "com.mashape.unirest:unirest-java:1.4.8",
                    "org.skyscreamer:jsonassert:1.3.0",
                    "com.cedarsoftware:json-io:4.4.0"
            ],
    ]
 */
}
