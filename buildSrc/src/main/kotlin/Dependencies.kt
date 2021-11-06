private val nonStableVersionMarker = listOf("alpha", "beta", "rc")

fun isStableVersion(version: String): Boolean =
    !nonStableVersionMarker.any { version.contains(it, ignoreCase = true) }