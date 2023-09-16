package org.intellij.sdk.language.step2;

import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.step1.SimpleLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * “ипы токенов, возвращаемых в результате лексического анализа, и типы узлов в дереве AST.
 * ¬се используемые типы элементов добавл€ютс€ в реестр,
 * который может быть перечислен или доступен по индексу.
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
