package org.intellij.sdk.language.step12;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.psi.PsiElement;
import com.intellij.psi.presentation.java.SymbolPresentationUtil;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.util.SimpleUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleDocumentationProvider extends AbstractDocumentationProvider {

  /**
   * url - документации
   */
  @Override
  public @Nullable List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
    return null;
  }

  /**
   * Извлекает ключ, значение, файл и комментарий к документации из простой записи ключ/значение
   * и возвращает форматированное представление информации.
   */
  @Override
  public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
    if (element instanceof SimpleProperty) {
      final String key = ((SimpleProperty) element).getKey();
      final String value = ((SimpleProperty) element).getValue();
      final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
      final String docComment = SimpleUtil.findDocumentationComment((SimpleProperty) element);

      return renderFullDoc(key, value, file, docComment);
    }
    return null;
  }

  /**
   * Предоставляет информацию, в каком файле определен ключ/значение простого языка.
   */
  @Override
  public @Nullable String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
    if (element instanceof SimpleProperty) {
      final String key = ((SimpleProperty) element).getKey();
      final String file = SymbolPresentationUtil.getFilePathPresentation(element.getContainingFile());
      return "\"" + key + "\" in " + file;
    }
    return null;
  }

  /**
   * Предоставляет документацию при наведении курсора мыши на простой языковой элемент.
   */
  @Override
  public @Nullable String generateHoverDoc(@NotNull PsiElement element, @Nullable PsiElement originalElement) {
    return generateDoc(element, originalElement);
  }

  /**
   * Создает строку ключ/значение для отображаемой документации.
   */
  private void addKeyValueSection(String key, String value, StringBuilder sb) {
    sb.append(DocumentationMarkup.SECTION_HEADER_START);
    sb.append(key);
    sb.append(DocumentationMarkup.SECTION_SEPARATOR);
    sb.append("<p>");
    sb.append(value);
    sb.append(DocumentationMarkup.SECTION_END);
  }

  /**
   * Создает отформатированную документацию
   */
  private String renderFullDoc(String key, String value, String file, String docComment) {
    StringBuilder sb = new StringBuilder();
    sb.append(DocumentationMarkup.DEFINITION_START);
    sb.append("Simple Property");
    sb.append(DocumentationMarkup.DEFINITION_END);
    sb.append(DocumentationMarkup.CONTENT_START);
    sb.append(value);
    sb.append(DocumentationMarkup.CONTENT_END);
    sb.append(DocumentationMarkup.SECTIONS_START);
    addKeyValueSection("Key:", key, sb);
    addKeyValueSection("Value:", value, sb);
    addKeyValueSection("File:", file, sb);
    addKeyValueSection("Comment:", docComment, sb);
    sb.append(DocumentationMarkup.SECTIONS_END);
    return sb.toString();
  }

}
