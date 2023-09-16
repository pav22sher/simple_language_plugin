package org.intellij.sdk.language.step7;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import org.intellij.sdk.language.SimpleLexerAdapter;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.step3.SimpleTokenSets;
import org.intellij.sdk.language.step5.SimpleAnnotator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Определяет поддержку функции "Поиск использований" на пользовательском языке.
 */
public class SimpleFindUsagesProvider implements FindUsagesProvider {

  @Nullable
  @Override
  public WordsScanner getWordsScanner() {
    return new DefaultWordsScanner(new SimpleLexerAdapter(),
            SimpleTokenSets.IDENTIFIERS,
            SimpleTokenSets.COMMENTS,
            TokenSet.EMPTY);
  }

  @Override
  public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
    return psiElement instanceof PsiNamedElement;
  }

  @Nullable
  @Override
  public String getHelpId(@NotNull PsiElement psiElement) {
    return null;
  }

  /**
   * Возвращает видимый пользователю тип указанного элемента, показанный в диалоговом окне
   */
  @NotNull
  @Override
  public String getType(@NotNull PsiElement element) {
    if (element instanceof SimpleProperty) {
      return "simple property";
    }
    return "";
  }

  /**
   * Возвращает расширенное, видимое пользователю имя указанного элемента
   */
  @NotNull
  @Override
  public String getDescriptiveName(@NotNull PsiElement element) {
    if (element instanceof SimpleProperty) {
      return ((SimpleProperty) element).getKey();
    }
    return "";
  }

  /**
   * Возвращает текст, представляющий указанный элемент PSI в дереве поиска использований.
   */
  @NotNull
  @Override
  public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
    if (element instanceof SimpleProperty) {
      return ((SimpleProperty) element).getKey() +
              SimpleAnnotator.SIMPLE_SEPARATOR_STR +
              ((SimpleProperty) element).getValue();
    }
    return "";
  }

}
