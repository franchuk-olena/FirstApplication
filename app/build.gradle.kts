plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.firstapplication"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.firstapplication"
        minSdk = 26
        targetSdk = 37
        versionCode = 2
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
        debug {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.coil)
}


tasks.register("printProjectInfo") {
    doLast {
        println("--- Інформація про модуль ---")
        println("Модуль: ${project.name}")
        println("Шлях до збірки: ${project.buildDir}")
    }
}