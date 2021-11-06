// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        jcenter()
        google()
        mavenLocal()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.plugin.kotlin)
        classpath(libs.plugin.kotlinSerialization)
        classpath(libs.plugin.android)
        classpath(libs.plugin.dexcount)
        classpath(libs.plugin.ktlint)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

allprojects {
    repositories {
        jcenter()
        google()
        maven {
            url = uri("https://maven.pkg.github.com/eadm/AndroidKit")
            credentials {
                username = System.getenv("GITHUB_USER") ?: project.properties["GITHUB_USER"] as String?
                password = System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN") ?: project.properties["GITHUB_PERSONAL_ACCESS_TOKEN"] as String?
            }
        }
        maven {
            url = uri("https://maven.pkg.github.com/eadm/ktlint-rules")
            credentials {
                username = System.getenv("GITHUB_USER") ?: project.properties["GITHUB_USER"] as String?
                password = System.getenv("GITHUB_PERSONAL_ACCESS_TOKEN") ?: project.properties["GITHUB_PERSONAL_ACCESS_TOKEN"] as String?
            }
        }
        maven { url = uri("https://jitpack.io") }
        mavenLocal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
