package ru.kazantsev.nsd.sdk.gradle_plugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.kazantsev.nsd.sdk.gradle_plugin.services.CodeRunnerService
import ru.kazantsev.nsd.sdk.gradle_plugin.services.NavigatorService
import java.io.File

open class SendScriptTask : DefaultTask() {

    companion object {
        const val NAME = "send_script"
    }

    override fun getGroup(): String? {
        return "nsd_sdk"
    }

    override fun getDescription(): String? {
        return "Отправляет в NSD скрипт для запуска"
    }

    @TaskAction
    fun action() {
        val navigator = NavigatorService.instance!!
        val runner = navigator.codeRunnerService
        runner.sendScript(navigator.sourceSetsService.consoleFilePath)
    }
}
