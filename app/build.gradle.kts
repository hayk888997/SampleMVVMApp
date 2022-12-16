plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android")
    id("androidx.navigation.safeargs")

}

android {
    namespace = "com.sample.appwithmvvm"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = "keyAlias"
            keyPassword = "keypasword"
            storeFile = file("full/path/to/file")
            storePassword = "storePassword"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    val navigationVersion = "2.3.5"
    val lifecycleVersion = "2.3.1"
    val koinVersion = "3.2.3"
    val retrofitVersion = "2.9.0"
    val okhttpLoggingVersion = "4.9.0"

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")

    //AndroidX lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    //Networking
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:${okhttpLoggingVersion}")


    // Koin main features for Android
    implementation ("io.insert-koin:koin-android:$koinVersion")

    // Navigation Graph
    implementation ("io.insert-koin:koin-androidx-navigation:$koinVersion")

    // Lottie
    implementation ("com.airbnb.android:lottie:5.2.0")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

}