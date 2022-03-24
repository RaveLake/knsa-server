import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.6.1")
    }
}

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("kapt") version "1.4.10" apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }
}
