plugins {
    `kotlin-dsl`
}

group = "quokkaui.build.plugin"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation("org.tomlj:tomlj:1.0.0")
}

gradlePlugin {
    plugins {
        register("QuokkaPlugin") {
            id = "quiokkaui.build.QuokkaPlugin"
            implementationClass = "quokkaui.build.QuokkaPlugin"
        }
    }
}
