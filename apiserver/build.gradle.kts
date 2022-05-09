group = "knu.notice"
version = "1"
val queryDSLVersion = "5.0.0"

plugins {
    war
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
    kotlin("kapt") version "1.4.10"
    idea
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    implementation("com.google.firebase", "firebase-admin", "6.8.1")
    implementation("com.squareup.okhttp3", "okhttp", "4.2.2")
    implementation("com.google.firebase", "firebase-admin", "6.10.0")
    implementation("com.querydsl:querydsl-jpa:$queryDSLVersion")
    kapt("com.querydsl:querydsl-apt:$queryDSLVersion:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.kafka:spring-kafka")
}

idea {
    module {
        val kaptMain = file("/apiserver/build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}