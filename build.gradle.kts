plugins {
    id("java")
    `maven-publish`
}

group = "com.stmarygate"
version = "1.0.14"

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
    implementation("ch.qos.logback", "logback-classic", "1.2.9")
    implementation("org.springframework.security:spring-security-core:6.2.1")
    implementation("org.springframework.security:spring-security-crypto:6.2.1")
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")
    implementation("org.jetbrains:annotations:24.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}