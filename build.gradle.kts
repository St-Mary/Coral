plugins {
    id("java")
    `maven-publish`
}

group = "com.stmarygate.common"
version = "1.0.6.dev.3"

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
            url = uri("https://maven.pkg.github.com/St-Mary/CommonLib")
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

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}