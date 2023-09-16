package org.intellij.sdk.language.step5;

import com.intellij.codeInsight.intention.impl.BaseIntentionAction;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import org.intellij.sdk.language.step2.SimpleElementFactory;
import org.intellij.sdk.language.psi.SimpleFile;
import org.intellij.sdk.language.psi.SimpleProperty;
import org.intellij.sdk.language.step1.SimpleFileType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

class SimpleCreatePropertyQuickFix extends BaseIntentionAction {

  private final String key;

  SimpleCreatePropertyQuickFix(String key) {
    this.key = key;
  }

  @NotNull
  @Override
  public String getText() {
    return "Create property '" + key + "'";
  }

  @NotNull
  @Override
  public String getFamilyName() {
    return "Create property";
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
    return true;
  }

  @Override
  public void invoke(@NotNull final Project project, final Editor editor, PsiFile file) throws IncorrectOperationException {
    ApplicationManager.getApplication().invokeLater(() -> {
      Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(SimpleFileType.INSTANCE, GlobalSearchScope.allScope(project));
      if (virtualFiles.size() == 1) {
        createProperty(project, virtualFiles.iterator().next());
      } else {
        final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(SimpleFileType.INSTANCE);
        descriptor.setRoots(ProjectUtil.guessProjectDir(project));
        final VirtualFile file1 = FileChooser.chooseFile(descriptor, project, null);
        if (file1 != null) {
          createProperty(project, file1);
        }
      }
    });
  }

  private void createProperty(final Project project, final VirtualFile file) {
    WriteCommandAction.writeCommandAction(project).run(() -> {
      SimpleFile simpleFile = (SimpleFile) PsiManager.getInstance(project).findFile(file);
      assert simpleFile != null;
      ASTNode lastChildNode = simpleFile.getNode().getLastChildNode();
      if (lastChildNode != null) {
        simpleFile.getNode().addChild(SimpleElementFactory.createCRLF(project).getNode());
      }
      // �����: �������� ������� �� ��������������, ����� � ����� ���� ����� ������ ������ ����� ��� �����
      SimpleProperty property = SimpleElementFactory.createProperty(project, key.replaceAll(" ", "\\\\ "), "");
      simpleFile.getNode().addChild(property.getNode());
      ((Navigatable) property.getLastChild().getNavigationElement()).navigate(true);
      Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
      assert editor != null;
      editor.getCaretModel().moveCaretRelatively(2, 0, false, false, false);
    });
  }

}
