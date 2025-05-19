val springBootVersion: String by project

plugins {
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":domain-jpa"))

    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator:$springBootVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    runtimeOnly("com.h2database:h2:2.2.224")

    testRuntimeOnly("com.h2database:h2:2.2.224")
}

tasks.bootJar.get().enabled = true
tasks.jar.get().enabled = false
