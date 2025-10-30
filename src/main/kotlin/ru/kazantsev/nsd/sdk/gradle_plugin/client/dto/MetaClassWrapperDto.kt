package ru.kazantsev.nsd.sdk.gradle_plugin.client.dto

/**
 * DTO с информацией по метаклассу
 */
class MetaClassWrapperDto  {
    var title: String = ""
    var fullCode: String = ""
    var caseCode: String? = null
    var classCode: String = ""
    var parent: String? = null
    var children: List<String> = listOf()
    var attributes: List<AttributeDto> = listOf()
    var description : String? = null
    var hasResponsible : Boolean = false
    var hasWorkflow  : Boolean = false
    var hardcoded : Boolean = false
}
