package org.intellij.sdk.language.step12;

import com.intellij.lang.Commenter;
import org.jetbrains.annotations.Nullable;

/**
 *  омментатор позвол€ет пользователю автоматически закомментировать строку кода под курсором или выбранный код
 */
public class SimpleCommenter implements Commenter {

  @Nullable
  @Override
  public String getLineCommentPrefix() {
    return "#";
  }

  @Nullable
  @Override
  public String getBlockCommentPrefix() {
    return "";
  }

  @Nullable
  @Override
  public String getBlockCommentSuffix() {
    return null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentPrefix() {
    return null;
  }

  @Nullable
  @Override
  public String getCommentedBlockCommentSuffix() {
    return null;
  }

}
