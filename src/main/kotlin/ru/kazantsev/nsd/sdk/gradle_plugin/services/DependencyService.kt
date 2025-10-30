package ru.kazantsev.nsd.sdk.gradle_plugin.services

import java.net.URI

/**
 * Подключает репозитории, добавляет зависимости
 */
class DependencyService(private val navigator: NavigatorService) {

    companion object {
        val DEV_DEPENDENCY_IDS = setOf(
            "ru.kazantsev.nsd:json_rpc_connector:1.+",
            "ru.kazantsev.nsd.sdk:global_variables:1.+"
        )
    }

    private var repositoryUri: URI = URI("https://maven.pkg.github.com/exeki/*")
    private var repositoryUsername: String? = System.getenv("GITHUB_USERNAME")
    private var repositoryPassword: String? = System.getenv("GITHUB_TOKEN")

    fun addRepositoriesToProject() {
        println("Добавляю репозитории:")
        val customRepo = navigator.project.repositories.maven {}
        customRepo.url = repositoryUri
        if (repositoryUsername != null) {
            customRepo.credentials.username = repositoryUsername
            customRepo.credentials.password = repositoryPassword
        }
        println("   maven $repositoryUri")
        navigator.project.repositories.add(customRepo)
    }

    fun addDevDependenciesToProject() {
        println("Добавляю зависимости:")
        DEV_DEPENDENCY_IDS.forEach {
            println("   implementation $it")
            navigator.project.dependencies.add("implementation", it)
        }
    }
}
