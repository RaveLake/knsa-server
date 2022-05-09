version = "1"

plugins {
    war
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    kotlin("kapt") version "1.4.10"
    idea
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("com.google.firebase", "firebase-admin", "6.8.1")
    implementation("com.squareup.okhttp3", "okhttp", "4.2.2")
    implementation("com.google.firebase", "firebase-admin", "6.10.0")
    implementation("org.springframework.kafka:spring-kafka")
}