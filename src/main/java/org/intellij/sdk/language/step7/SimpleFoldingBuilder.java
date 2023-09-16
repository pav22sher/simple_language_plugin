package org.intellij.sdk.language.step7;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.FoldingGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.JavaRecursiveElementWalkingVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiLiteralUtil;
import com.intellij.util.containers.ContainerUtil;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.step5.SimpleAnnotator;
import org.intellij.sdk.language.util.SimpleUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Правила сворачивания кода
 */
public class SimpleFoldingBuilder extends FoldingBuilderEx implements DumbAware {

  @Override
  public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
    // Инициализируйте группу областей сворачивания, которые будут расширяться/сворачиваться вместе.
    FoldingGroup group = FoldingGroup.newGroup(SimpleAnnotator.SIMPLE_PREFIX_STR);
    // Инициализируйте список областей сворачивания
    List<FoldingDescriptor> descriptors = new ArrayList<>();

    root.accept(new JavaRecursiveElementWalkingVisitor() {

      @Override
      public void visitLiteralExpression(@NotNull PsiLiteralExpression literalExpression) {
        super.visitLiteralExpression(literalExpression);

        String value = PsiLiteralUtil.getStringLiteralContent(literalExpression);
        if (value != null && value.startsWith(SimpleAnnotator.SIMPLE_PREFIX_STR + SimpleAnnotator.SIMPLE_SEPARATOR_STR)) {
          Project project = literalExpression.getProject();
          String key = value.substring(
                  SimpleAnnotator.SIMPLE_PREFIX_STR.length() + SimpleAnnotator.SIMPLE_SEPARATOR_STR.length()
          );
          SimpleProperty simpleProperty = ContainerUtil.getOnlyItem(SimpleUtil.findProperties(project, key));
          if (simpleProperty != null) {
            // Добавим дескриптор сворачивания для литерального выражения в этом узле.
            TextRange textRange = literalExpression.getTextRange();
            descriptors.add(
                    new FoldingDescriptor(literalExpression.getNode(),
                    new TextRange(textRange.getStartOffset() + 1, textRange.getEndOffset() - 1),
                    group, Collections.singleton(simpleProperty))
            );
          }
        }
      }
    });

    return descriptors.toArray(FoldingDescriptor.EMPTY);
  }

  @Nullable
  @Override
  public String getPlaceholderText(@NotNull ASTNode node) {
    if (node.getPsi() instanceof PsiLiteralExpression psiLiteralExpression) {
      String text = PsiLiteralUtil.getStringLiteralContent(psiLiteralExpression);
      if (text == null) {
        return null;
      }

      String key = text.substring(
              SimpleAnnotator.SIMPLE_PREFIX_STR.length() + SimpleAnnotator.SIMPLE_SEPARATOR_STR.length()
      );

      SimpleProperty simpleProperty = ContainerUtil.getOnlyItem(
              SimpleUtil.findProperties(psiLiteralExpression.getProject(), key)
      );
      if (simpleProperty == null) {
        return StringUtil.THREE_DOTS;
      }

      String propertyValue = simpleProperty.getValue();
      if (propertyValue == null) {
        return StringUtil.THREE_DOTS;
      }

      return propertyValue.replaceAll("\n", "\\n").replaceAll("\"", "\\\\\"");
    }

    return null;
  }

  @Override
  public boolean isCollapsedByDefault(@NotNull ASTNode node) {
    return true;
  }

}
