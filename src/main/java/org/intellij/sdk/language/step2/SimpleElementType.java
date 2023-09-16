package org.intellij.sdk.language.step2;

import com.intellij.psi.tree.IElementType;
import org.intellij.sdk.language.step1.SimpleLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SimpleElementType extends IElementType {

  public SimpleElementType(@NotNull @NonNls String debugName) {
    super(debugName, SimpleLanguage.INSTANCE);
  }

}
