plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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
                srcDirs("src\\main\\res", "src\\main\\res\\layouts\\Gestion_utilisateur",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_bornes", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_boutique",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_vehicules", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_flotte",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_forum", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_utilisateur\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_bornes\\layout", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_flotte\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_boutiques\\layout", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_forum\\layout",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\Gestion_vehicules\\layout", "src\\main\\res", "src\\main\\res\\layouts\\Gestion_boutique\\layout",
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


}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}