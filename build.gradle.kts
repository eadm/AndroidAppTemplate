// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        jcenter()
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.plugin.kotlin)
        classpath(libs.plugin.kotlinSerialization)
        classpath(libs.plugin.android)
        classpath(libs.plugin.dexcount)
        classpath(libs.plugin.ktlint)
        classpath(libs.plugin.gradleVersionUpdates)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "com.github.ben-manes.versions")
}

allprojects {
    repositories {
        jcenter()
        google()
        github(project, "https://maven.pkg.github.com/eadm/AndroidKit")
        github(project, "https://maven.pkg.github.com/eadm/ktlint-rules")
        maven("https://jitpack.io")
        mavenLocal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
