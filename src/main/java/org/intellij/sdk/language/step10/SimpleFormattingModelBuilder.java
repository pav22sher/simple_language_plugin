package org.intellij.sdk.language.step10;

import com.intellij.formatting.*;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import org.intellij.sdk.language.psi.SimpleTypes;
import org.intellij.sdk.language.step1.SimpleLanguage;
import org.intellij.sdk.language.step10.SimpleBlock;
import org.jetbrains.annotations.NotNull;

/**
 * ������ �������������� - ���������� ��� ������� ���� �� ����� � ������������ �������
 */
public class SimpleFormattingModelBuilder implements FormattingModelBuilder {

  /**
   * ����������� ���������� ������ �������������� ��� ������� �����, ����������� ��������� ������� PSI � ��� �������� ��������.
   */
  @Override
  public @NotNull FormattingModel createModel(@NotNull FormattingContext formattingContext) {
    final CodeStyleSettings codeStyleSettings = formattingContext.getCodeStyleSettings();
    return FormattingModelProvider
            .createFormattingModelForPsiFile(formattingContext.getContainingFile(),
                    new SimpleBlock(formattingContext.getNode(),
                            Wrap.createWrap(WrapType.NONE, false),
                            Alignment.createAlignment(),
                            createSpaceBuilder(codeStyleSettings)),
                    codeStyleSettings);
  }

  private static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
    return new SpacingBuilder(settings, SimpleLanguage.INSTANCE)
            .around(SimpleTypes.SEPARATOR)
            .spaceIf(settings.getCommonSettings(SimpleLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS)
            .before(SimpleTypes.PROPERTY)
            .none();
  }


}
