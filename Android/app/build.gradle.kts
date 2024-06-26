import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.ssafy.travelcollector"
    compileSdk = 33

    defaultConfig {

        applicationId = "com.ssafy.travelcollector"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_APP_KEY", properties.getProperty("kakao_api_key"))
        buildConfigField("String", "NAVER_CLIENT_ID", properties.getProperty("naver_client_id"))
        buildConfigField("String", "NAVER_CLIENT_SECRET", properties.getProperty("naver_client_secret"))
        buildConfigField("String", "NAVER_CLIENT_NAME", properties.getProperty("naver_client_name"))
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

    viewBinding{
        enable = true
    }

    buildFeatures{
        buildConfig = true
    }
}

dependencies {

    implementation("com.google.android.gms:play-services-location:21.1.0")
    val lifecycle_version = "2.6.1"
    val arch_version = "2.2.0"
    val nav_version = "2.4.2"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")


    implementation("com.navercorp.nid:oauth-jdk8:5.9.0")
//    implementation("com.kakao.sdk:v2-user:2.12.1")

    implementation("com.kakao.sdk:v2-all:2.19.0")


    implementation("io.github.ParkSangGwon:tedpermission-normal:3.3.0")

    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")


}