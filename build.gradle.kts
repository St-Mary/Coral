plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

group = "com.stmarygate"
version = "1.0.16"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/St-Mary/Coral")
            credentials {
                username = (System.getenv("GITHUB_ACTOR") ?: "").toString()
                password = (System.getenv("GITHUB_TOKEN") ?: "").toString()
            }
        }
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("io.netty:netty-all:4.1.101.Final")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // BCrypt
    implementation("org.springframework.security:spring-security-core:6.2.1")
    implementation("org.springframework.security:spring-security-crypto:6.2.1")
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")

    // JWT
    implementation("io.jsonwebtoken:jjwt:0.12.3")

    implementation("org.jetbrains:annotations:24.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.stmarygate.coral.Main"
    }
    archiveBaseName.set("coral")
    archiveClassifier.set("")
    archiveVersion.set(version.toString())
}

tasks.test {
    useJUnitPlatform()
}