package ru.kazantsev.nsd.sdk.gradle_plugin

import ru.kazantsev.nsd.sdk.gradle_plugin.services.NavigatorService

abstract class Extension(private val navigator: NavigatorService) {


    /**
     * Установить путь до выполняемого в NSD скрипта
     * @param path путь до выполняемого в NSD скрипта
     */
    fun setConsoleScriptPath(path: String) {
        navigator.sourceSetsService.consoleFilePath = path
    }

    /**
     * Указать ID инсталляции, параметры которой будет получены из файла конфигурации для использовании в SDK
     * @param installationId ID инсталляции
     */
    fun setInstallation(installationId: String) {
        navigator.setInstallation(installationId)
    }

    /**
     * Указать ID инсталляции, параметры которой будет получены из файла конфигурации по указаннму пути для использовании в SDK
     * @param installationId ID инсталляции
     * @param connectorParamsPath путь до файла конфигурации
     */
    fun setInstallation(installationId: String, connectorParamsPath: String) {
        navigator.setInstallation(installationId, connectorParamsPath)
    }

    /**
     * Указать все переметры инсталлляции, которая будет использования в SDK
     * @param installationId ID инсталляции
     * @param scheme схема (http или https)
     * @param host хост
     * @param accessKey ключ доступа
     * @param ignoreSLL игнорировать ssl
     */
    fun setInstallation(
        installationId: String,
        scheme: String,
        host: String,
        accessKey: String,
        ignoreSLL: Boolean
    ) {
        navigator.setInstallation(
            installationId,
            scheme,
            host,
            accessKey,
            ignoreSLL
        )
    }
}
