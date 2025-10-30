package ru.kazantsev.nsd.sdk.gradle_plugin.services

import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import java.io.File
import java.io.FileWriter


class SourceSetsService(private val navigator: NavigatorService) {
    companion object {

        const val DEFAULT_SCRIPT_PATH = "src/main/groovy/console.groovy"
        const val scriptSourceSetPath: String = "src/main/scripts"
        var sourceSets: MutableSet<String> = mutableSetOf(
            "src\\main\\groovy",
            "src\\main\\modules",
            scriptSourceSetPath
        )
        var packageFolders: MutableSet<String> = mutableSetOf(
            "attrFiltration",
            "calculationOnEdit",
            "eventActionConditions",
            "eventActions",
            "migration\\console",
            "migration\\scheduledTasks",
            "permissions",
            "scheduledTasks",
            "stateActions\\fromState",
            "stateActions\\fromStateCondition",
            "stateActions\\inState",
            "stateActions\\inStateCondition"
        )
        val NEW_CONSOLE_FILE_TEXT = """
           //Скрипт из этого файла может быть отправлен в NSD.
           //Для отправки скрипта выполните задачу "send_script".
           //Результат выполнения скрипта отобразиться в консоли.  
           
           import static ru.kazantsev.nsd.sdk.global_variables.ApiPlaceholder.*
           import static ru.kazantsev.nsd.sdk.global_variables.GlobalVariablesPlaceholder.*
           import ru.naumen.core.server.script.spi.*
        """.trimIndent()
    }

    var consoleFilePath: String = DEFAULT_SCRIPT_PATH

    fun createConsoleFile() {
        val consoleFile = File(consoleFilePath)
        if (!consoleFile.exists()) {
            println("The console file does not exist on the path \"$consoleFilePath\", creating...")
            val parent = consoleFile.parentFile
            if (!parent.exists()) parent.mkdirs()
            consoleFile.createNewFile()
            val writer = FileWriter(consoleFile)
            writer.write(NEW_CONSOLE_FILE_TEXT)
            writer.close()
            println("Created.")
        } else println("The console file exists.")
    }

    fun createSourceSetsFolders() {
        println("Checking src folders...")
        val sourceSetContainer = navigator.project.extensions.getByType(SourceSetContainer::class.java)
        val main = sourceSetContainer.maybeCreate(SourceSet.MAIN_SOURCE_SET_NAME)
        var created = 0
        sourceSets.forEach {
            val file = File("${navigator.project.projectDir.path}\\$it")
            if (!file.exists()) {
                println("Creating src folder \"${it}\".")
                file.mkdirs()
                created++
            }
            main.java.srcDir(it)
        }
        if (created == 0) println("All folders exists.")
    }

    fun createScriptPackages() {
        println("Checking package folders...")
        val scriptFolder = File(scriptSourceSetPath)
        if (!scriptFolder.exists()) scriptFolder.mkdirs()
        var created = 0
        packageFolders.forEach {
            val file = File("${navigator.project.projectDir.path}/${scriptSourceSetPath}/${it}")
            if (!file.exists()) {
                println("Creating script package folder \"$it\".")
                file.mkdirs()
                created++
            }
        }
        if (created == 0) println("All folders exists.")
    }
}
