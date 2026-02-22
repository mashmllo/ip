package sora.command;

import java.util.ArrayList;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.search.TaskMatcher;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that finds a task based on keyword in the description
 * using normal and fuzzy search.
 * <p>
 * The command first performs a substring match. If no substring match is found,
 * it applies Jaro-Winkler similarity to detect approximate matches.
 */
public class FindCommand implements Command {

    private final String keyword;
    private final TaskMatcher matcher;

    /**
     * Constructs a {@link FindCommand} with the given keyword.
     *
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.trim().toLowerCase();
        this.matcher = new TaskMatcher(this.keyword);
    }

    /**
     * Executes the find command by searching tasks containing the keyword.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException If an error occurs during command execution.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        if (taskManager == null) {
            throw new NullPointerException("TaskManager object should not be null");
        }

        if (ui == null) {
            throw new NullPointerException("Ui object should not be null");
        }

        ArrayList<Task> matches = matcher.findMatchingTasks(taskManager.getTasks());

        ui.showSearchResult(taskManager.getTasks(), matches, keyword);
    }
}
