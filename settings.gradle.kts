rootProject.name = "gradle_plugin"
pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/exeki/*")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        mavenCentral()
    }
}
