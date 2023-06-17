import quokkaui.build.SupportConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.github.heesung6701.quokkaui.catalog"
    compileSdkVersion = SupportConfig.COMPILE_SDK_VERSION
    buildToolsVersion = SupportConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = "com.github.heesung6701.quokkaui.catalog"
        minSdk = SupportConfig.DEFAULT_MIN_SDK_VERSION
        targetSdk = SupportConfig.TARGET_SDK_VERSION
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = SupportConfig.INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}
val buildFromMaven = ("true" == project.properties["quokka.catalog.buildFromMaven"])
println("[QuokkaBuild]: buildFromMaven is $buildFromMaven")

dependencies {
    if (buildFromMaven) {
        implementation("com.github.heesung6701.QuokkaUI:touchdelegate:+")
        implementation("com.github.heesung6701.QuokkaUI:anchor:+")
    } else {
        implementation(project(":touchdelegate:touchdelegate"))
        implementation(project(":anchor:anchor"))
        implementation(project(":picker:component"))
    }

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // https://maven.google.com/web/index.html?q=compose-#androidx.compose:compose-bom:2023.03.00
    api(platform("dev.chrisbanes.compose:compose-bom:2023.03.00"))

    implementation(libs.androidxActivityCompose)
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    testImplementation(libs.junit)
    androidTestImplementation(libs.testExtJunit)
    androidTestImplementation(libs.espressoCore)
}