pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Papuga"
include(":app")
include(":core")
include(":api")
include(":utils")
include(":feature_main")
include(":feature_home")
include(":feature_playlist")
include(":player")
