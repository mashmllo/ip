package sora.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import sora.command.FindCommand;
import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

public class FindCommandTest {

    /**
     * Simple fake Task for testing.
     */
    private static class FakeTask extends Task {

        public FakeTask(String name) {
            super(name);
        }
    }

    /**
     * Fake TaskManager that returns a pre-defined list.
     */
    private static class FakeTaskManager extends TaskManager {

        private final ArrayList<Task> tasks;

        public FakeTaskManager(ArrayList<Task> tasks) {
            this.tasks = tasks;
        }
    }

    /**
     * Fake Ui to capture search results.
     */
    private static class FakeUi extends Ui {
        private ArrayList<Task> receivedMatches;
        private String receivedKeyword;

        @Override
        public void showSearchResult(ArrayList<Task> matchingResult, String keyword) {
            this.receivedMatches = matchingResult;
            this.receivedKeyword = keyword;
        }
    }

    @Test
    public void execute_nullTaskManager_throwsException() {
        FakeUi ui = new FakeUi();
        FindCommand cmd = new FindCommand("test");

        assertThrows(NullPointerException.class, ()
                -> cmd.execute(null, ui));
    }

    @Test
    public void execute_nullUi_throwsException() {
        ArrayList<Task> tasks = new ArrayList<>();
        FakeTaskManager mgr = new FakeTaskManager(tasks);

        FindCommand cmd = new FindCommand("test");

        assertThrows(NullPointerException.class, ()
                -> cmd.execute(mgr, null));
    }

    @Test
    public void execute_noMatch_returnsEmptyList() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FakeTask("run"));
        tasks.add(new FakeTask("swim"));

        FakeTaskManager mgr = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand cmd = new FindCommand("read");

        cmd.execute(mgr, ui);

        assertNotNull(ui.receivedMatches);
        assertTrue(ui.receivedMatches.isEmpty());
    }

    @Test
    public void constructor_trimsAndLowercasesKeyword() throws SoraException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FakeTask("do homework"));

        FakeTaskManager mgr = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand cmd = new FindCommand("  HOMEWORK  ");

        cmd.execute(mgr, ui);

        assertEquals("homework", ui.receivedKeyword);
    }

    @Test
    public void execute_emptyTaskList_stillCallsUi() throws SoraException {
        ArrayList<Task> tasks = new ArrayList<>();

        FakeTaskManager mgr = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand cmd = new FindCommand("anything");

        cmd.execute(mgr, ui);

        assertNotNull(ui.receivedMatches);
        assertTrue(ui.receivedMatches.isEmpty());
    }

    @Test
    public void execute_fuzzyMatch_typoStillMatches() throws SoraException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FakeTask("do homework"));

        FakeTaskManager mgr = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand cmd = new FindCommand("homewrk");

        cmd.execute(mgr, ui);

        assertNotNull(ui.receivedMatches);
        assertEquals("homewrk", ui.receivedKeyword);
    }

    @Test
    public void execute_fuzzyMatchCompletelyDifferentWord_noMatch() throws SoraException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FakeTask("wash car"));

        FakeTaskManager mgr = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand cmd = new FindCommand("homework");

        cmd.execute(mgr, ui);

        assertTrue(ui.receivedMatches.isEmpty());
    }


    @Test
    public void execute_keywordOnlySpaces_noCrash() throws SoraException {
        assertThrows(IllegalArgumentException.class, ()
                -> new FindCommand("      "));
    }


    @Test
    public void execute_extremelyLongKeyword_noCrash() throws SoraException {

        String longKeyword = "a".repeat(5000);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new FakeTask("short task"));

        FakeTaskManager manager = new FakeTaskManager(tasks);
        FakeUi ui = new FakeUi();

        FindCommand command = new FindCommand(longKeyword);

        command.execute(manager, ui);

        assertNotNull(ui.receivedMatches);
    }
}
