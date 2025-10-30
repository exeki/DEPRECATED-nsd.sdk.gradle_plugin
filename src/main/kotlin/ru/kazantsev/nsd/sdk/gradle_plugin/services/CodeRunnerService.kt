package ru.kazantsev.nsd.sdk.gradle_plugin.services

import ru.kazantsev.nsd.basic_api_connector.Connector
import java.io.File

/**
 * Отправляет код в NSD
 */
class CodeRunnerService(private val navigator: NavigatorService) {

    fun sendScript(filePath : String) {
        val file = File(filePath)
        navigator.checkInstallationIsSpecifiedElseThrow()
        val connector = Connector(navigator.connectorParams)
        val message: String? = connector.execFile(file)
        if (message != null) {
            println("------------NSD SCRIPT RESULT------------")
            println(message)
            println("-----------------------------------------")
        }
    }
}
