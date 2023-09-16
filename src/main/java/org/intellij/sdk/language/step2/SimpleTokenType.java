package org.intellij.sdk.language.step2;

import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.step1.SimpleLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * ���� �������, ������������ � ���������� ������������ �������, � ���� ����� � ������ AST.
 * ��� ������������ ���� ��������� ����������� � ������,
 * ������� ����� ���� ���������� ��� �������� �� �������.
 */
public class SimpleTokenType extends IElementType {

  public SimpleTokenType(@NotNull @NonNls String debugName) {
    super(debugName, SimpleLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return "SimpleTokenType." + super.toString();
  }

}
