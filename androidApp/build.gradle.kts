plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    kotlin("plugin.serialization") version libs.versions.kotlin
}

android {
    namespace = "com.rtriani.easytask.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.rtriani.easytask.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

configurations.all {
    resolutionStrategy {
        force ("com.google.guava:guava:31.1-jre")
        force ("junit:junit:4.13.2")
    }
}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.appcompat)
    implementation(libs.testng)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization.json)
    implementation("androidx.navigation:navigation-compose:2.8.4")
    implementation("androidx.compose.material:material:1.7.5")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.5")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation ("androidx.test:core:1.5.0")
    testImplementation ("androidx.test.ext:junit:1.1.5")
    testImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("org.mockito:mockito-core:5.5.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("net.bytebuddy:byte-buddy:1.14.6")
    testImplementation("net.bytebuddy:byte-buddy-agent:1.14.6")

    debugImplementation(libs.compose.ui.tooling)
}