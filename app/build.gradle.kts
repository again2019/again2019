plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
    id("kotlin-parcelize")
}


android {
    namespace = "com.goingbacking.goingbacking"
    compileSdk = App.compileSdk
    buildToolsVersion = App.buildTools


    defaultConfig {
        applicationId = "com.goingbacking.goingbacking"
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = App.versionCode
        versionName = App.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
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
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

}


dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":presentation"))

    // Basic
    implementation(Basic.appcompat)

    // Kotlin
    implementation(Kotlin.reflection)

    // Firebase
    implementation(Firebase.bom)
    implementation(Firebase.anayltics)
    implementation(Firebase.auth)
    implementation(Firebase.firestore)
    implementation(Firebase.messaging)
    implementation(Firebase.gmsServiceAuth)

//    implementation platform('com.google.firebase:firebase-bom:30.1.0')
//    implementation 'com.google.firebase:firebase-analytics:17.5.0'
//    implementation 'com.google.firebase:firebase-auth:19.3.2'
//    implementation 'com.google.android.gms:play-services-auth:19.0.0'
//    implementation 'com.google.firebase:firebase-firestore-ktx:24.3.0'
//    implementation 'com.google.firebase:firebase-messaging:23.0.8'

    //Dagger Hilt
    implementation(DI.daggerHiltAndroid)
    implementation(DI.daggerHiltComponse)
    kapt(DI.daggerHiltAndroidAnnotation)

//    implementation 'com.google.dagger:hilt-android:2.40.5'
//    implementation 'androidx.appcompat:appcompat:1.2.0'
//    implementation 'com.google.android.material:material:1.4.+'
//    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
//    implementation 'androidx.databinding:databinding-runtime:7.1.2'
//    kapt 'com.google.dagger:hilt-android-compiler:2.40.5'
//    implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03'
//    kapt 'androidx.hilt:hilt-compiler:1.0.0'

    // Image
    implementation(Image.glide)

    // Design
    implementation(Design.material)
    implementation(Design.constraintLayout)

    // data store
    implementation(DataStore.proto)
    implementation(DataStore.preferences)
    implementation(DataStore.preferencesCore)

//    implementation 'androidx.datastore:datastore-preferences:1.0.0' // Preference DataStore
//    implementation("androidx.datastore:datastore:1.0.0") // Proto DataStore
//    implementation  "androidx.datastore:datastore-core:1.0.0-alpha01"

    // Coroutine
    implementation(Coroutine.core)
    implementation(Coroutine.android)

    // Network
    implementation(Network.retrofit)
    implementation(Network.retrofitConverter)
    implementation(Network.loggingInterceptor)
    implementation(Network.retrofitAdapter)
    implementation(Network.retrofitOkhttp)
    implementation(Network.retrofitScalar)

    // DataBinding
    implementation(DataBinding.runtime)

//    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
//    implementation 'com.squareup.retrofit2:converter-gson:2.7.2'
//    implementation 'com.squareup.retrofit2:converter-scalars:2.1.0'
//    implementation 'com.squareup.okhttp3:logging-interceptor:4.9.3'
//
//    implementation 'com.google.code.gson:gson:2.8.6'

}

