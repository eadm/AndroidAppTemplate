plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    compileSdk = appVersions.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.nobird.template"

        minSdk = appVersions.versions.minSdk.get().toInt()
        targetSdk = appVersions.versions.targetSdk.get().toInt()

        versionCode = appVersions.versions.versionCode.get().toInt()
        versionName = appVersions.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
    }

    lintOptions {
        isWarningsAsErrors = false // after fixing existing errors set to true for fixing warnings
        isAbortOnError = false // after fixing existing errors set to true for failing fast
        lintConfig = rootProject.file("lint.xml")
        htmlOutput = rootProject.file("reports/lint/lint.html")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
//            signingConfig signingConfigs.debug
        }

        debug {
            isMinifyEnabled = true
            isShrinkResources = false
            versionNameSuffix = "-debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro"),
                file("proguard-rules-debug.pro")
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
    }
}

dependencies {
    implementation(
        fileTree("libs") {
            include("*.jar")
        }
    )
    implementation(libs.kotlin)

    implementation(libs.dagger)
    kapt(libs.daggerCompiler)

    implementation(libs.appCompat)
    implementation(libs.ktx)
    implementation(libs.recyclerview)
    implementation(libs.viewPager)
    implementation(libs.constraintLayout)
    implementation(libs.material)
    implementation(libs.flexbox)
    implementation(libs.fragment)
    implementation(libs.fragmentKtx)
    implementation(libs.viewBindingDelegate)

    implementation(libs.bundles.androidKit)

//    implementation(libraries.permissionDispatcher)
//    kapt libraries.permissionDispatcherProcessor
    implementation(libs.kotpref)
    implementation(libs.cicerone)

    implementation(libs.archExtensions)
    implementation(libs.archViewModel)

    implementation(libs.retrofit)
    implementation(libs.kotlinSerialization)
    implementation(libs.kotlinSerializationAdapter)

    implementation(libs.glide)
    implementation(libs.glideTransformations)

    /*--Room--*/
    implementation(libs.roomRuntime)
    implementation(libs.roomKtx)
    kapt(libs.roomCompiler)

    implementation(libs.security)
    implementation(libs.sqlcipher)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockitoCore)
    testImplementation(libs.mockitoKt)

    debugImplementation(libs.bundles.flipperDebug)

    ktlintRuleset(libs.ktlintRules)
}

ktlint {
    android.set(true)
    disabledRules.set(listOf("package-name"))
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        !isStableVersion(candidate.version)
    }
}

apply(plugin = "com.getkeepsafe.dexcount")
