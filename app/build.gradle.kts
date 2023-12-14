plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.example.electrigo"
    compileSdk = 34



    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
        exclude ("META-INF/NOTICE")
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE")
        exclude ("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.txt")
        exclude ("META-INF/ASL2.0")
        exclude ("META-INF/LICENSE.md")
    }

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
    buildFeatures {
        viewBinding = true
        dataBinding = true
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

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("com.google.code.gson:gson:2.9.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.10")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.compose.ui:ui-graphics:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.activity:activity-compose:1.8.1")
    implementation ("androidx.compose.ui:ui:1.5.4")
    implementation ("androidx.compose.ui:ui-graphics:1.5.4")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation ("androidx.compose.material3:material3:1.1.2")
    testImplementation ("junit:junit:4.13.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0-rc01")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.4")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.5.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0-rc01")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.28")
    implementation (platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))
    implementation ("com.squareup.okhttp3:okhttp")
    implementation ("com.squareup.okhttp3:logging-interceptor")
    implementation ("com.stripe:stripe-android:20.35.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("com.github.bumptech.glide:okhttp3-integration:4.12.0")
    implementation ("com.github.bumptech.glide:recyclerview-integration:4.12.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("androidx.appcompat:appcompat:1.3.1")


    implementation ("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation ("com.github.kittinunf.fuel:fuel-json:2.3.1")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")


    implementation ("com.stripe:stripe-android:19.1.1")

    //Network call
    implementation ("com.squareup.okhttp3:okhttp:4.4.0")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.google.android.gms:play-services-wallet:18.1.2")
    implementation ("com.airbnb.android:lottie:4.1.0")
    implementation("com.twilio.sdk:twilio:9.2.1")

    implementation ("com.sun.mail:android-mail:1.6.7")
    implementation ("com.sun.mail:android-activation:1.6.7")
    implementation ("com.itextpdf:itextpdf:5.5.13.2")




}