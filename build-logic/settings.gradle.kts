dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":plugin")
