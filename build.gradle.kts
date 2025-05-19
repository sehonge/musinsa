import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val springBootVersion: String by project
val mockitoKotlinVersion: String by project
val fixtureMonkeyKotlinVersion: String by project

plugins {
    id("org.springframework.boot") version "3.2.6"
    id("io.spring.dependency-management") version "1.1.5"

    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("kapt") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24" apply false
}

allprojects {
    group = "kr.musinsa"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.kapt")
        plugin("java")
        plugin("idea")
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        kapt("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")

        testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
        testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
        testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter-kotlin:$fixtureMonkeyKotlinVersion")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.compileKotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    tasks.compileTestKotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
