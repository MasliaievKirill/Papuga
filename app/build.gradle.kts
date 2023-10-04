plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.androidHilt)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
}

android {
    namespace = "com.masliaiev.papuga"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.masliaiev.papuga"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.test.manifest)

    implementation(project(path = ":core"))
    implementation(project(path = ":api"))
    implementation(project(path = ":feature_main"))

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)



    implementation(libs.lifecycle.viewmodel.compose)
    implementation (libs.androidx.navigation)
//    implementation (libs.lifecycle.viewmodel.ktx)
    //Coroutines
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)


}