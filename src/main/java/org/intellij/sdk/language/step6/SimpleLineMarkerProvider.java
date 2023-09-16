package org.intellij.sdk.language.step6;

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.impl.source.tree.java.PsiJavaTokenImpl;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.step1.SimpleIcons;
import org.intellij.sdk.language.step5.SimpleAnnotator;
import org.intellij.sdk.language.util.SimpleUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * �������� ������� �������� �������������� ��� � ������� ������� �� �����
 */
public class SimpleLineMarkerProvider extends RelatedItemLineMarkerProvider {

  @Override
  protected void collectNavigationMarkers(@NotNull PsiElement element,
                                          @NotNull Collection<? super RelatedItemLineMarkerInfo<?>> result) {
    //��� ������ ���� ������� java � ���������� � �������� ������������� ��������
    if (!(element instanceof PsiJavaTokenImpl) || !(element.getParent() instanceof PsiLiteralExpression)) {
      return;
    }

    //��������� ������ ���������� � �������� simple:
    PsiLiteralExpression literalExpression = (PsiLiteralExpression) element.getParent();
    String value = literalExpression.getValue() instanceof String ? (String) literalExpression.getValue() : null;
    if ((value == null) || !value.startsWith(SimpleAnnotator.SIMPLE_PREFIX_STR + SimpleAnnotator.SIMPLE_SEPARATOR_STR)) {
      return;
    }

    // �������� ��������
    Project project = element.getProject();
    String possibleProperties = value.substring(
            SimpleAnnotator.SIMPLE_PREFIX_STR.length() + SimpleAnnotator.SIMPLE_SEPARATOR_STR.length()
    );
    final List<SimpleProperty> properties = SimpleUtil.findProperties(project, possibleProperties);
    if (properties.size() > 0) {
      NavigationGutterIconBuilder<PsiElement> builder = NavigationGutterIconBuilder.create(SimpleIcons.FILE)
                      .setTargets(properties)
                      .setTooltipText("Navigate to Simple language property");
      result.add(builder.createLineMarkerInfo(element));
    }
  }

}
