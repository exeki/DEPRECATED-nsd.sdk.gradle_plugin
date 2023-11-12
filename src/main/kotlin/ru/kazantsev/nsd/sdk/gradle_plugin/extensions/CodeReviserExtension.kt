package ru.kazantsev.nsd.sdk.gradle_plugin.extensions

import org.gradle.api.Project
import java.io.File

open class CodeReviserExtension(private val project: Project, private val fakeClassesExtension: FakeClassesExtension) {
    companion object {
        private const val fakeClassesRealName = "IScriptDtObject"
    }

    var processPath: String = "src/main"
    var outPath: String = "sdk/out"
    private val processDir: File
    private val outDir: File

    init {
        val projectDir = project.projectDir.path
        this.processDir = File("$projectDir\\$processPath")
        this.outDir = File("$projectDir\\${this.outPath}")
        project.task("sdk_build_src") {
            it.group = "nsd_sdk"
            it.description = "Creates files from all sources in the " +
                    "specified directory with code ready to be placed in NSD"
            it.doLast {
                this.process()
            }
        }
    }

    private fun process() {
        processFile(processDir)
    }

    //TODO на будущее
    private fun processModule(directory: File) {
        if (directory.isFile) throw RuntimeException("Got file in method \"processModule\" but expects directory")
        var text = ""
        var packageString = ""
        var imports = ""
        directory.listFiles().forEach { file ->
            if (file.isDirectory) throw RuntimeException("Directories not allowed in module package")
        }

    }

    private fun processFile(file: File) {
        if (this.outDir.isFile) throw RuntimeException("Out directory cant by a file")
        if (file.isFile) {
            var filePath = file.name
            var lookFolder = file.parentFile
            while (true) {
                filePath = "${lookFolder.name}\\$filePath"
                if (lookFolder == processDir) break
                else lookFolder = lookFolder.parentFile
            }
            val targetFile = File("${this.outDir.path}\\$filePath")
            targetFile.parentFile.mkdirs()
            var text = file.readText()
            if (fakeClassesExtension.generatedClassNames == null || fakeClassesExtension.generatedClassNames?.size == 0) {
                throw RuntimeException("Cant find generatedClassNames in fakeClassesExtension")
            }
            fakeClassesExtension.generatedClassNames?.forEach {
                text = text.replace(it, fakeClassesRealName)
            }
            targetFile.writeText(text)
        } else {
            file.listFiles()?.forEach {
                processFile(it)
            }
        }
    }
}