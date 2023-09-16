# Simple Language Sample [![JetBrains IntelliJ Platform SDK Docs](https://jb.gg/badges/docs.svg)][docs]
*Reference: [Custom Language Support Tutorial in IntelliJ SDK Docs][docs:custom_language_support_tutorial]*

## Quickstart

Defines a new language, _Simple language_ with support for syntax highlighting, annotations, code completion, and other features.

*Reference: [Plugin Extension Points in IntelliJ SDK Docs][docs:ep]*


[docs]: https://plugins.jetbrains.com/docs/intellij/
[docs:custom_language_support_tutorial]: https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html
[docs:ep]: https://plugins.jetbrains.com/docs/intellij/plugin-extensions.html


### Модули доступны во всех продуктах семейства IntelliJ:
* com.intellij.modules.platform;
* com.intellij.modules.lang;
* com.intellij.modules.vcs;
* com.intellij.modules.xml;
* com.intellij.modules.xdebugger.

### Модули доступны только в соответствующих продуктах:
* com.intellij.modules.java – IntelliJ IDEA;
* com.intellij.modules.ruby – RubyMine;
* com.intellij.modules.python – PyCharm;

Чтобы загрузить классы каждого плагина, IDEA использует раздельные загрузчики классов.
Это позволяет использовать различные версии библиотеки, 
даже если они используются самой IDEA или другим плагином.
По-умолчанию, основной загрузчик классов загружает только те классы, 
которые не были найдены загрузчиком плагина.
Тем не менее в plugin.xml, в секции depends можно определить зависимости от других плагинов.
В таком случае, загрузчики классов этих плагинов будут использованы для разрешения 
не найденных классов в текущем плагине.
Это позволяет ссылаться на классы из других плагинов.

Intellij IDEA предоставляет концепт расширений и точек расширения, 
которые позволяют взаимодействовать с другими плагинами и ядром IDEA.
Если требуется, чтобы плагин позволял расширять его функциональность, 
то необходимо определить одну или несколько точек расширения.
Каждая такая точка определяет класс или интерфейс, который определяет протокол доступа к ней.
Это же касается и расширений плагина.
Регистрация точек расширения:
```xml
<extensionPoints>
<extensionPoint name="MyExtensionPoint1" beanClass="MyPlugin.MyBeanClass1"/>t6
</extensionPoints>
```


Также Intellij IDEA предоставляет концепт действий (actions).
Действие – это класс, наследуемый от AnAction,
чей метод actionPerformed() вызывается, когда выбран элемент меню или кнопка тулбара.
Действия объединяются в группы, которые также могут содержать вложенные группы.
Группы действий могут быть отображены как меню или тулбары.
Подгруппы отображаются как подменю.

Сервис – это компонент, загружаемый по требованию, когда плагин вызывает метод getService() класса ServiceManager.
Intellij IDEA гарантирует, что будет создан только один экземпляр сервиса, независимо от того сколько раз был вызван метод.
Сервисы подразделяются по уровням подобно компонентам, т.е. на сервисы уровня приложения, проекта и модуля,
которым соответствуют точки расширения applicationService, projectService и moduleService соответственно.
```xml
<extensions defaultExtensionNs="com.intellij">
<!-- сервис уровня приложения -->
<applicationService serviceInterface="Mypackage.MyServiceInterfaceClass" serviceImplementation="Mypackage.MyServiceImplClass">         
</applicationService>
    <!-- сервис уровня проекта -->
      <projectService serviceInterface="Mypackage.MyProjectServiceImplClass" serviceImplementation="Mypackage.MyProjectServiceImplClass">         
      </projectService>
</extensions>
 ```

Виртуальная файловая система (VFS) – это компонент IntelliJ IDEA,
инкапсулирующий большинство функций для работы с файлами.
Наиболее эффективный путь слежения за событиями VFS – это реализация интерфейса BulkFileListener 
и подписка на VirtualFileManager.VFS_CHANGES.
Этот API предоставляет список всех изменений, произошедших во время операции обновления, 
что позволяет производить пакетную обработку.
Альтернативный путь – реализация интерфейса VirtualFileListener 
и регистрация его посредством VirtualFileManager.addFileListener().
Этот вариант позволяет обрабатывать изменения поодиночке.
