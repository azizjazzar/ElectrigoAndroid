plugins {
    id("com.android.application")
    id("kotlin-android")
}
android {

    namespace = "com.example.electrigo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.electrigo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildFeatures {
            viewBinding = true

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main") {
            res {
                srcDirs(
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_utilisateur",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_bornes",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutique",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_vehicules",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_flotte",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_forum",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_utilisateur\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_bornes\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_flotte\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutiques\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_forum\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_vehicules\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutique\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutiques\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutique",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutique\\layout"
                )
            }
        }
    }

    dependencies {
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
        implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
        implementation("com.google.android.material:material:1.3.0")
        implementation("androidx.appcompat:appcompat:1.2.0")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
        implementation("com.google.android.material:material:1.8.0")
        implementation("androidx.core:core-ktx:1.9.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation("androidx.compose.ui:ui:1.1.0")
        implementation("androidx.compose.ui:ui-graphics:1.1.0")
        implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")
        implementation("androidx.compose.material3:material3:1.1.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0")
        debugImplementation("androidx.compose.ui:ui-tooling:1.1.0")
        debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0")
        implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    }
}
dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
