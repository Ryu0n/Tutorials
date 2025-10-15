plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    // Mono, Flux를 사용하기 위한 필수 라이브러리
    implementation("io.projectreactor:reactor-core:3.6.8")

    // Kotlin 코루틴과 연동을 위한 권장 라이브러리
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")

    // https://mvnrepository.com/artifact/io.netty/netty-all
    implementation("io.netty:netty-all:4.1.24.Final")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
