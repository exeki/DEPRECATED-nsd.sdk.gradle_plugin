# NSD SDK Gradle Plugin

Плагин поставляет ряд функций, упрощающих написание скриптов и модулей для NSD.
Все примеры будут указаны с использованием kotlin dsl синтаксиса, использование в файлах gradle с groovy dls так же возможно, но синтаксис может чуть отличаться.

# Подключение плагина

1. Создайте переменную среды GITHUB_USERNAME на своем ПК для текущего пользователя. Укажите в ней почту, на которую у вас зарегистрирован аккаунт Github.
2. Создайте переменную среды GITHUB_TOKEN на своем ПК для текущего пользователя. Укажите в ней персональный токен доступа. Рекомендуется использовать [fine-grained токен](https://github.com/settings/tokens?type=beta) со стандартными настройками, но должен подойти так же и [classic токен](https://github.com/settings/tokens).
3. Перезагрузите ПК, что бы переменные инициализировались.
4. Для использования SDK рекомендуется использовать в качестве базы сгенерированный Intellij IDEA проект с параметрами:
- Language - groovy
- Build system - gradle (тест функционала с maven не проводился)
- JDK - Amazon Corretto 11 (думаю подойдет любая 11 версия)
- Groovy SDK - 3.0.19
- Gradle DSL - kotlin
  Как использовать SDK в другой IDE я не знаю, тк пользуюсь только Intellij IDEA, но gradle - это универсальный инструмент, и в теории не должно быть проблемой запустить SDK в любой другой IDE, которая будет поддержавать gradle.
  Если у вас есть подобный опыт и выхотите им поделится - пишите мне, размещу информацию тут.
5. Разместите в нужной вам инсталляции NSD [этот модуль](https://github.com/exeki/nsd.modules.sdk_controller)
6. В вашем проекте подключите мой репозиторий в ключ pluginManagement.repositories в файл settings.gradle.kts:
```settings.gradle.kts
rootProject.name = "random_scripts"
pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/exeki/*")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        mavenCentral()
    }
}
```
7. Подключите плагин в файле build.gradle.kts вашего проекта:
```build.gradle.kts
plugins {
    id("java")
    id("groovy")
    //Версия может быть устаревшей, последнюю версию можете найти в разделе packages текушего репозиторирия
    id("nsd_sdk") version "1.4.1"
}
```

Если вы не хотите указывать pluginManagement репозиторий в каждом проекте, вы можете в домашней директории gradle **{user.home}/.gradle** создать файл **init.gradle.kts**, в нем указать что для каждого вашего проекта, необходимо внедрять данный репозиторий:
```build.gradle.kts
settingsEvaluated {
    pluginManagement {
        repositories {
            maven {
                name = "exekiGithubRepo"
                url = uri("https://maven.pkg.github.com/exeki/*")
                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
            mavenCentral()
        }
    }
}
```
После этого не нужно будет в каждом проекте указывать репозитории для плагинов, но это не исключит возможность дополнения проекта дополнительными репозиториями.

# Указание инсталляции

Большая чуть функций SDK требует непосредственного взаимодействия с NSD, для этого внутри SDK есть коннектор для системы.
Что бы установить соединение, нужно передать его параметры. Для этого используйте один из следующий вариантов:

### Первый вариант подключения

Этот вариант рекомендуется.

1. В соответствии с [описанием библиотеки nsd.basic_api_connector](https://github.com/exeki/nsd.basic_api_connector/blob/master/README.md) из раздела "Конфигурационный файл" создайте конфигурационный файл, указав в нем параметры инсталляции, для которой будут писаться скрипты.
2. Вызовите метод **setInstallation** в расширении **sdk** файла **build.gradle.kts** вашего проекта, в качестве аргумета передайте ID указанной в конфигурационном файле инсталляции:
```build.gradle.kts
sdk {
    setInstallation("MY_INSTALLATION_ID")
}
```
3. Выполните пересборку gradle проекта

### Второй вариант подключения

1. В соответствии с [описанием библиотеки nsd.basic_api_connector](https://github.com/exeki/nsd.basic_api_connector/blob/master/README.md) из раздела "Конфигурационный файл" создайте конфигурационный файл, указав в нем параметры инсталляции, для которой будут писаться скрипты, но можете расположить его в любом месте.
2. Вызовите метод **setInstallation** в расширении **sdk** файла **build.gradle.kts** вашего проекта, в качестве аргуметов передайте ID указанной в конфигурационном файле инсталляции и путь до конфигурационного файла:
```build.gradle.kts
sdk {
    setInstallation("MY_INSTALLATION_ID", "C:\\Users\\ekazantsev\\someFolder\\params.json")
}
```
3. Выполните пересборку gradle проекта

### Третий вариант подключения

Этот вариант НЕ рекомендуется.

1. Вызовите метод **setInstallation** в расширении **sdk** файла **build.gradle.kts** вышего проекта, в качестве аргуметов передайте:
- installationId: String - ID инсталляции
- scheme: String - протокол (http или https)
- host: String - хост инсталляции
- accessKey: String - ключ доступа
- ignoreSLL: Boolean - игнорирование SSL при обращении к инсталляции
```build.gradle.kts
sdk {
    setInstallation(
        "MY_SD",
        "https",
        "my.nsd.ru",
        "g23g132-vf182-xcb2vh4-2dfg21-028vpk",
        true
    )
}
```
3. Выполните пересборку gradle проекта

# Поддерживаемый функционал

## Включение всех функций по умолчанию

Можно подключить все функции SDK одной строкой.
```build.gradle.kts
sdk.default("MY_INSTALLATION_ID")
```
Это подключит все перечисленные ниже функции в конфигурации по умолчанию.
В качестве параметра нужно указать ID инсталлляции (подробнее смотрите в разделе "указание инсталляции" (первый вариант)).
При этом варианте метод **setInstallation** вызывать не нужно.

## Автоматическое подключение зависимостей

При использовании этой функции плагин подключает:

**Репозитории для зависимостей:**
- MavenLocal
- MavenCentral
- Мой репозиторий GitHub: https://maven.pkg.github.com/exeki/*

**Используемые при разработке или для обработок библиотеки:**
- [nsd.sdk.global_variables](https://github.com/exeki/nsd.sdk.global_variables) - используется как плейсхолдер API системы, полезна для написания скриптов;
- [nsd.basic_api_connector](https://github.com/exeki/nsd.basic_api_connector) - используется для соеденинения с NSD по http, то есть позволяет запускать операции/обработки NSD из IDE (в тч отправлять скрипты).
- [nsd.json_rpc_connector](https://github.com/exeki/nsd.json_rpc_connector) - используется для соеденинения с NSD по http. Не забудьте разместить модуль [jsonRpc](https://github.com/exeki/ru.itsm365.jsonRpc_doc) в инсталляции, если хотите использовать данную библиотеку.

Для использования вызовите в расширешении **sdk** в файле **build.gradle.kts** вашего проекта методы:
```build.gradle.kts
sdk {
    addRepositories() //Добавляет репозитоии
    addDevDependencies() //Добавляет dev зависимости
    addAppDependencies() //Добавляет библиотеки исспользуемые в приложении
}
```

## Выполнение скриптов в NSD

Для использования требуется подключенная инсталляция.
Плагин может отправлять на выполнение в NSD ваши скрипты и возвращать вам результат.
Для этого создайте в вашем проекте файл **src/main/console.groovy**. В нем вы можете написать свой скрипт в тч с использованием фейковых классов и плейсхолдер api (для этого в инсталляции нужен модуль **sdkPlaceholder** из проекта [nsd.sdk.global_variables](https://github.com/exeki/nsd.sdk.global_variables)).
Для отправки скрипта выполните задачу **send_script**.

Пример скрипта:
```groovy
import static ru.kazantsev.nsd.sdk.global_variables.ApiPlaceholder.*

def root = utils.get('root', [:])
return root.UUID
```
В консоли проекта вернет:
```
> Task :send_script
------------NSD SCRIPT RESULT------------
root$101
-----------------------------------------

BUILD SUCCESSFUL in 6s
1 actionable task: 1 executed
23:48:39: Execution finished 'send_script'.
```

Для удобства запуска своего скрипта в intellij ide вы можете добавить конфигурацию запуска,
которая будет ссылаться на gradle задачу **send_script**, и запускать свой скрипт нажатием на кнопку Play в верхнем правом углу IDE.
Но помните, что выполняться будет всегда скрипт из файла **src/main/console.groovy**.

В случае, если вы не хотите использовать путь до файла по умолчанию, вы можете вызывать метод **setConsoleScriptPath** в расширении, передав в него путь до файла относительно проекта.
```build.gradle.kts
sdk {
    setInstallation("MY_INSTALLATION_ID")
    setConsoleScriptPath("src\\main\\anotherFolder\\anotherFile.groovy")
}
```

## Автоматическое создания структуры каталогов в проекте

Эта функция подготавливает проект к работе:
1. Создает и подключает к gradle папки source sets с именами: scripts, modules;
2. Создает структуру пакетов внутри source set`ов;
3. Создает файл запускаемого скрипта;

Для использования вызовите методы:
```build.gradle.kts
sdk {
    createSourceDirs()
    createScriptPackages()
    createConsoleFile()
}
```

**Созданные папки и файлы могут не сразу отобразится в IDE, проверьте папку проекта в проводнике. Если файлы там есть - просто внесите какое-то изменение в файлы проекта или перезагрузите проект.**
