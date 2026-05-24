import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import org.gradle.api.plugins.quality.Checkstyle

plugins {
    application
    java
    checkstyle
    id("com.github.spotbugs") version "6.4.4"
    jacoco
    //id("info.solidsoft.pitest") version "1.19.0"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

checkstyle {
    toolVersion = "10.26.1"
    configDirectory.set(layout.projectDirectory.dir("config/checkstyle"))
    isIgnoreFailures = false
    maxWarnings = 0
}

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    effort = Effort.MAX
    reportLevel = Confidence.LOW
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
val excluded = listOf(
    "**/gui/**",
    "**/*View*",
    "**/*GUI*",
    "**/*Enum*",
    "**/Color.class"
)
tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "COMPLEXITY"
                value = "COVEREDRATIO"
                minimum = "1.0".toBigDecimal()
            }
        }
    }
    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude(excluded)
            }
        })
    )
}
tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    //finalizedBy(tasks.pitest)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}
