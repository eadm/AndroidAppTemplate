enableFeaturePreview("VERSION_CATALOGS")
include(":app")

dependencyResolutionManagement {
    versionCatalogs {
        create("appVersions") {
            from(files("gradle/app.versions.toml"))
        }
    }
}