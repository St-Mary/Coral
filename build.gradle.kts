plugins {
    id("java")
    `maven-publish`
}

group = "com.github.saintmary"
version = "1.0-SNAPSHOT"

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
            url = uri("https://maven.pkg.github.com/St-Mary/StMary-CommonLib")
            credentials {
                username = project.findProperty("GITHUB_ACTOR").toString()
                password = project.findProperty("GITHUB_TOKEN").toString()
            }
        }
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    implementation("io.netty:netty-all:4.1.101.Final")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}