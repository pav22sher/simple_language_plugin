package org.intellij.sdk.language.step5;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.step4.SimpleSyntaxHighlighter;
import org.intellij.sdk.language.util.SimpleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Выделить и аннотировать любой код на основе определенных правил
 */
public class SimpleAnnotator implements Annotator {
  public static final String SIMPLE_PREFIX_STR = "simple";
  public static final String SIMPLE_SEPARATOR_STR = ":";

  @Override
  public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
    //Убедитесь, что элемент PSI является выражением
    if (!(element instanceof PsiLiteralExpression literalExpression)) {
      return;
    }

    //Убедитесь, что элемент Psi содержит строку, начинающуюся с префикса и разделителя
    String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
    if (value == null || !value.startsWith(SIMPLE_PREFIX_STR + SIMPLE_SEPARATOR_STR)) {
      return;
    }

    //Определите диапазоны текста
    TextRange prefixRange = TextRange.from(element.getTextRange().getStartOffset(), SIMPLE_PREFIX_STR.length() + 1);
    TextRange separatorRange = TextRange.from(prefixRange.getEndOffset(), SIMPLE_SEPARATOR_STR.length());
    TextRange keyRange = new TextRange(separatorRange.getEndOffset(), element.getTextRange().getEndOffset() - 1);

    //выделите префикс "simple" и разделитель ":"
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(prefixRange)
            .textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
            .create();
    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(separatorRange)
            .textAttributes(SimpleSyntaxHighlighter.SEPARATOR)
            .create();


    //Получить список свойств для данного ключа
    String key = value.substring(SIMPLE_PREFIX_STR.length() + SIMPLE_SEPARATOR_STR.length());
    List<SimpleProperty> properties = SimpleUtil.findProperties(element.getProject(), key);
    if (properties.isEmpty()) {
      holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
              .range(keyRange)
              .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
              .withFix(new SimpleCreatePropertyQuickFix(key))
              .create();
    } else {
      holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
              .range(keyRange)
              .textAttributes(SimpleSyntaxHighlighter.VALUE)
              .create();
    }
  }

}
