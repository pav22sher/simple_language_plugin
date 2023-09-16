# Simple Language Sample [![JetBrains IntelliJ Platform SDK Docs](https://jb.gg/badges/docs.svg)][docs]
*Reference: [Custom Language Support Tutorial in IntelliJ SDK Docs][docs:custom_language_support_tutorial]*

## Quickstart

Defines a new language, _Simple language_ with support for syntax highlighting, annotations, code completion, and other features.

*Reference: [Plugin Extension Points in IntelliJ SDK Docs][docs:ep]*


[docs]: https://plugins.jetbrains.com/docs/intellij/
[docs:custom_language_support_tutorial]: https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html
[docs:ep]: https://plugins.jetbrains.com/docs/intellij/plugin-extensions.html


### ������ �������� �� ���� ��������� ��������� IntelliJ:
* com.intellij.modules.platform;
* com.intellij.modules.lang;
* com.intellij.modules.vcs;
* com.intellij.modules.xml;
* com.intellij.modules.xdebugger.

### ������ �������� ������ � ��������������� ���������:
* com.intellij.modules.java � IntelliJ IDEA;
* com.intellij.modules.ruby � RubyMine;
* com.intellij.modules.python � PyCharm;

����� ��������� ������ ������� �������, IDEA ���������� ���������� ���������� �������.
��� ��������� ������������ ��������� ������ ����������, 
���� ���� ��� ������������ ����� IDEA ��� ������ ��������.
��-���������, �������� ��������� ������� ��������� ������ �� ������, 
������� �� ���� ������� ����������� �������.
��� �� ����� � plugin.xml, � ������ depends ����� ���������� ����������� �� ������ ��������.
� ����� ������, ���������� ������� ���� �������� ����� ������������ ��� ���������� 
�� ��������� ������� � ������� �������.
��� ��������� ��������� �� ������ �� ������ ��������.

Intellij IDEA ������������� ������� ���������� � ����� ����������, 
������� ��������� ����������������� � ������� ��������� � ����� IDEA.
���� ���������, ����� ������ �������� ��������� ��� ����������������, 
�� ���������� ���������� ���� ��� ��������� ����� ����������.
������ ����� ����� ���������� ����� ��� ���������, ������� ���������� �������� ������� � ���.
��� �� �������� � ���������� �������.
����������� ����� ����������:
```xml
<extensionPoints>
<extensionPoint name="MyExtensionPoint1" beanClass="MyPlugin.MyBeanClass1"/>t6
</extensionPoints>
```


����� Intellij IDEA ������������� ������� �������� (actions).
�������� � ��� �����, ����������� �� AnAction,
��� ����� actionPerformed() ����������, ����� ������ ������� ���� ��� ������ �������.
�������� ������������ � ������, ������� ����� ����� ��������� ��������� ������.
������ �������� ����� ���� ���������� ��� ���� ��� �������.
��������� ������������ ��� �������.

������ � ��� ���������, ����������� �� ����������, ����� ������ �������� ����� getService() ������ ServiceManager.
Intellij IDEA �����������, ��� ����� ������ ������ ���� ��������� �������, ���������� �� ���� ������� ��� ��� ������ �����.
������� �������������� �� ������� ������� �����������, �.�. �� ������� ������ ����������, ������� � ������,
������� ������������� ����� ���������� applicationService, projectService � moduleService ��������������.
```xml
<extensions defaultExtensionNs="com.intellij">
<!-- ������ ������ ���������� -->
<applicationService serviceInterface="Mypackage.MyServiceInterfaceClass" serviceImplementation="Mypackage.MyServiceImplClass">         
</applicationService>
    <!-- ������ ������ ������� -->
      <projectService serviceInterface="Mypackage.MyProjectServiceImplClass" serviceImplementation="Mypackage.MyProjectServiceImplClass">         
      </projectService>
</extensions>
 ```

����������� �������� ������� (VFS) � ��� ��������� IntelliJ IDEA,
��������������� ����������� ������� ��� ������ � �������.
�������� ����������� ���� �������� �� ��������� VFS � ��� ���������� ���������� BulkFileListener 
� �������� �� VirtualFileManager.VFS_CHANGES.
���� API ������������� ������ ���� ���������, ������������ �� ����� �������� ����������, 
��� ��������� ����������� �������� ���������.
�������������� ���� � ���������� ���������� VirtualFileListener 
� ����������� ��� ����������� VirtualFileManager.addFileListener().
���� ������� ��������� ������������ ��������� ����������.
