plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.github.heesung6701.quokkaui.picker"
    compileSdkVersion = quokkaui.build.SupportConfig.COMPILE_SDK_VERSION
    buildToolsVersion = quokkaui.build.SupportConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = quokkaui.build.SupportConfig.DEFAULT_MIN_SDK_VERSION
        targetSdk = quokkaui.build.SupportConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = quokkaui.build.SupportConfig.INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        all {
            consumerProguardFiles("proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidxCoreKtx)
    implementation(libs.androidxRecyclerView)
    implementation(libs.androidxConstraintLayout)
    implementation(libs.coroutinesAndroid)

    testImplementation(libs.junit)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoKotlin)
    androidTestImplementation(libs.testExtJunit)
    androidTestImplementation(libs.testExtJunitKtx)
    androidTestImplementation(libs.espressoCore)
    androidTestImplementation(libs.androidxTestUiAutomator)
}