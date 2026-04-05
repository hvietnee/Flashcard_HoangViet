plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.hoangviet.flashcard"
    compileSdk = 34 // Dùng bản 34 cho ổn định nhất hiện tại

    defaultConfig {
        applicationId = "com.hoangviet.flashcard"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    // --- HOÀNG VIỆT THÊM ĐOẠN NÀY ĐỂ BẬT VIEWBINDING ---
    buildFeatures {
        viewBinding = true
    }
    // ------------------------------------------------

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    // Thư viện giúp biến dữ liệu Flow thành LiveData để hiển thị lên màn hình
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    ksp(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Thư viện hỗ trợ biến Flow thành LiveData cho ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    // Thư viện hỗ trợ viewModelScope để chạy tác vụ ngầm (Coroutine)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.activity:activity-ktx:1.8.0")
}