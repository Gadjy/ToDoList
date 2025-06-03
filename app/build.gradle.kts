plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.example.todolist"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.todolist"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation ("io.coil-kt:coil-compose:2.5.0")
    implementation ("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    implementation ("androidx.compose.runtime:runtime-livedata:1.7.8")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation ("com.google.accompanist:accompanist-permissions:0.35.0-alpha")

    implementation ("androidx.work:work-runtime-ktx:2.9.0")
    implementation ("androidx.compose.material3:material3-adaptive:1.0.0-alpha06")

    implementation ("androidx.media3:media3-exoplayer:1.2.1")
    implementation ("androidx.media3:media3-ui:1.2.1")

    implementation ("io.coil-kt:coil-compose:2.5.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    implementation ("com.google.dagger:hilt-android:2.51.1")
    kapt ("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    implementation ("androidx.room:room-runtime:2.7.0")
    kapt ("androidx.room:room-compiler:2.7.0")
    implementation ("androidx.room:room-ktx:2.7.0")

    implementation ("androidx.navigation:navigation-compose:2.7.6")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}