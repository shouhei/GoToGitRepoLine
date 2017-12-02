import com.google.common.collect.Iterables;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.BalloonBuilder;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.awt.RelativePoint;
import git4idea.GitUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Collection;



public class GoToGitRepoLineAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        final com.intellij.openapi.editor.Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        CaretModel caretModel = editor.getCaretModel();
        LogicalPosition logicalPosition = caretModel.getLogicalPosition();
        Project project = e.getProject();
        if (project == null) {
            showMessage("Can't find project.");
            return;
        }
        VirtualFile virtualFile = project.getBaseDir();
        GitRepositoryManager m = GitUtil.getRepositoryManager(project);
        GitRepository repo = m.getRepositoryForFile(virtualFile);
        if (repo == null) {
            showMessage("It's not git repository.");
            return;
        }
        String revision = repo.getCurrentRevision();
        Collection<GitRemote> remotes = repo.getRemotes();
        GitRemote origin = Iterables.get(remotes, 0);
        String base = origin.getFirstUrl();
        FileEditorManager manager = FileEditorManager.getInstance(project);
        VirtualFile files[] = manager.getSelectedFiles();
        String file_path = files[0].toString().replace(virtualFile.toString(), "");

        String url = createUrl(base, revision, file_path, String.valueOf(logicalPosition.line + 1));
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(url), null);
        showMessage("Send \"" + url + "\" to clipboard.");
    }

    private String createUrl(String base, String revision, String path, String line) {
        return base + "/blob/" + revision + path +"#L"+ line;
    }

    private void showMessage(String message) {
        BalloonBuilder balloonBuilder = JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(message, MessageType.INFO, null);
        final Balloon balloon = balloonBuilder.createBalloon();
        balloon.show(new RelativePoint( new Point(0, 0)), Balloon.Position.above);
    }
}