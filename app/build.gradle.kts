plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.navigation.safe.args)
}

android {
    namespace = "com.amaterisa.movielistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.amaterisa.movielistapp"
        minSdk = 26
        targetSdk = 34
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
            buildConfigField("String", "AUTH_TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1Njc0MjFmZWZmYmJmY2IzNmZjNDk3MWQ1ODYzN2U1ZSIsIm5iZiI6MTcyNTM5OTMxMi40NzM4NTUsInN1YiI6IjY2ZDc3ZDJiYjA1NTM2YWI2OWI4OGU1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4sjzYREfPm-PrdtmZaLuwFU5q_wqSHO8EZpUDwd6kGE\"")
        }
        debug {
            buildConfigField("String", "AUTH_TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1Njc0MjFmZWZmYmJmY2IzNmZjNDk3MWQ1ODYzN2U1ZSIsIm5iZiI6MTcyNTM5OTMxMi40NzM4NTUsInN1YiI6IjY2ZDc3ZDJiYjA1NTM2YWI2OWI4OGU1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4sjzYREfPm-PrdtmZaLuwFU5q_wqSHO8EZpUDwd6kGE\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    implementation(libs.okhttp)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.glide)
    ksp(libs.glide.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}