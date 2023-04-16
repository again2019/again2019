
object App {
    const val compileSdk = 33
    const val minSdk = 27
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val buildTools = "30.0.3"
}

object Basic {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
}

object Kotlin {
    const val reflection = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinReflectionVersion}"
}

object View {
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewpagerVersion}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayoutVersion}"

}

object Firebase {
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBomVersion}"
    const val anayltics = "com.google.firebase:firebase-analytics:${Versions.firebaseAnalyticsVersion}"
    const val auth = "com.google.firebase:firebase-auth:${Versions.firebaseAuthVersion}"
    const val firestore = "com.google.firebase:firebase-firestore-ktx:${Versions.firebaseFireStoreVersion}"
    const val messaging = "com.google.firebase:firebase-messaging:${Versions.firebaseFirebaseMessagingVersion}"

    const val gmsServiceAuth = "com.google.android.gms:play-services-auth:${Versions.gmsPlayServicesAuth}"
}

object DI {
    const val daggerHiltAndroid =
        "com.google.dagger:hilt-android:${Versions.daggerHiltAndroidVersion}"
    const val daggerHiltAndroidAnnotation =
        "com.google.dagger:hilt-android-compiler:${Versions.daggerHiltAndroidVersion}"
    const val daggerHiltComponse =
        "androidx.hilt:hilt-navigation-compose:${Versions.daggerHiltComposeVersion}"

}

object Image {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
}

object Design {
    const val material = "com.google.android.material:material:${Versions.materialVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
}

object Coroutine {
    const val core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesVersion}"
    const val android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroidVersion}"
}

object DataStore {
    const val proto = "androidx.datastore:datastore:${Versions.dataStoreVersion}"
    const val preferences = "androidx.datastore:datastore-preferences:${Versions.dataStoreVersion}"
    const val preferencesCore =
        "androidx.datastore:datastore-preferences-core:${Versions.dataStoreVersion}"
}

object Network {
    // retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val retrofitConverter =
        "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"

    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
    const val retrofitAdapter =
        "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    const val retrofitOkhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    const val retrofitScalar =
        "com.squareup.retrofit2:converter-scalars:${Versions.retrofitVersion}"


    // ktor
    const val core = "io.ktor:ktor-client-core:${Versions.ktorVersion}"

    const val cio = "io.ktor:ktor-client-cio:${Versions.ktorVersion}"
    const val ktorOkhttp = "io.ktor:ktor-client-okhttp:${Versions.ktorVersion}"

    const val logging = "io.ktor:ktor-client-logging:${Versions.ktorVersion}"

    const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}"
    const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}"

    const val auth = "io.ktor:ktor-client-auth:${Versions.ktorVersion}"
}

object DataBinding {
    const val runtime = "androidx.databinding:databinding-runtime:${Versions.DataBindingVersion}"
}

object Versions {

    //Basic
    const val appcompatVersion = "1.2.0"


    //Gradle
    const val gradleVersion = "7.2.2"

    //Kotlin
    const val kotlinVersion = "1.8.10"
    const val kotlinReflectionVersion = "1.6.21"

    //View
    const val viewpagerVersion = "1.0.0"
    const val swipeRefreshLayoutVersion = "1.1.0"


    //Firebase
    const val firebaseBomVersion = "30.1.0"
    const val firebaseAnalyticsVersion = "17.5.0"
    const val firebaseAuthVersion = "19.3.2"
    const val firebaseFirebaseMessagingVersion = "23.0.8"
    const val firebaseFireStoreVersion = "24.3.0"
    const val firebaseCoroutineVersion = "1.1.1"

    const val gmsPlayServicesAuth = "19.0.0"


    //Dagger Hilt
    const val daggerHiltAndroidVersion = "2.45"
    const val daggerHiltComposeVersion = "1.0.0"


    //Network
    const val retrofitVersion = "2.9.0"
    const val okHttpVersion = "4.8.0"
    const val ktorVersion = "2.2.3"
    const val gsonVersion = "2.9.0"


    //Image
    const val glideVersion = "4.11.0"

    //Design
    const val materialVersion = "1.4.0"
    const val constraintLayoutVersion = "2.1.3"

    //Coroutine
    const val coroutinesVersion = "1.6.4"
    const val coroutinesAndroidVersion = "1.6.4"

    //ThirdParty
    const val calendarVersion = "1.0.3"
    const val staticCalendarVersion = "1.0.4"
    const val dynamicCalendarVersion = "0.4.5"
    const val dotsIndicatorVersion = "4.3"
    const val mpChartVersion = "3.1.0"
    const val aaChartVersion = "7.1.0"
    const val lottieVersion = "4.1.0"
    const val balloon = "1.3.3"

    //Navigation
    const val navigationVersion = "2.4.2"

    //DataStore
    const val dataStoreVersion = "1.0.0"
    const val dataStoreCoreVersion = "1.1.0"

    //Ktx
    const val fragmentKtxVersion = "1.5.2"
    const val coreKtxVersion = "1.3.0"
    const val activityVersion = "1.2.2"
    const val fragmentVersion = "1.3.3"
    const val workRuntimeVersion = "2.7.1"

    //LifeCycle
    const val lifecycleVersion = "2.5.1"

    //DataBinding
    const val DataBindingVersion = "7.1.2"

    //Etc
    const val desugarJdkVersion = "1.0.9"
}