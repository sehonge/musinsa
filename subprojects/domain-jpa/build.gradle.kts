val springBootVersion: String by project
val querydslVersion: String by project

plugins {
    id("org.springframework.boot")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

noArg {
    annotation("jakarta.persistence.Entity")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
    api("com.querydsl:querydsl-jpa:$querydslVersion:jakarta")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jakarta")
}

tasks.bootJar.get().enabled = false
tasks.jar.get().enabled = true
