package ru.kazantsev.nsd.sdk.gradle_plugin.services

import ru.kazantsev.nsd.sdk.gradle_plugin.tasks.CreateConsoleFileTask
import ru.kazantsev.nsd.sdk.gradle_plugin.tasks.SendScriptTask

/**
 * Вызывает процедуры после инициализации плагина и введенных значений
 */
class AfterEvaluateService(private val navigator: NavigatorService) {
    fun process() {
        println()
        println("NSD SDK")

        if (navigator.installationIsSpecified) {
            navigator.project.tasks.register(
                CreateConsoleFileTask.NAME,
                CreateConsoleFileTask::class.java
            )
            navigator.project.tasks.register(
                SendScriptTask.NAME,
                SendScriptTask::class.java
            )
            println()
            println("CONSOLE FILE")
            navigator.sourceSetsService.createConsoleFile()
        }

        println()
        println("DEPENDENCY MANAGER")
        navigator.dependencyService.addRepositoriesToProject()
        navigator.dependencyService.addDevDependenciesToProject()
        println()
        println("SOURCE SETS")
        navigator.sourceSetsService.createSourceSetsFolders()
        navigator.sourceSetsService.createConsoleFile()
        navigator.sourceSetsService.createScriptPackages()
    }
}
