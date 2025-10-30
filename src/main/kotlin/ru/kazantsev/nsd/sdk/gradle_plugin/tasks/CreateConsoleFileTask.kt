package ru.kazantsev.nsd.sdk.gradle_plugin.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.kazantsev.nsd.sdk.gradle_plugin.services.NavigatorService

class CreateConsoleFileTask: DefaultTask() {
    companion object {
        const val NAME = "create_console_file"
    }

    override fun getGroup(): String {
        return "nsd_sdk"
    }

    override fun getDescription(): String {
        return "Создает файл, предназначенный для написания отправляемых в NSD скриптов"
    }

    @TaskAction
    fun action() {
        NavigatorService.instance!!.sourceSetsService.createConsoleFile()
    }
}
