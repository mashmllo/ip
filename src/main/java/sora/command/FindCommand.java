package sora.command;

import java.util.ArrayList;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that finds a task based on keyword in the description.
 */
public class FindCommand implements Command {

    private final String keyword;

    /**
     * Constructs a {@link FindCommand} with the given keyword.
     * @param keyword Keyword to search for.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
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
        assert taskManager != null : "TaskManager object should not be null";
        assert ui != null : "Ui object should not be null";

        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Task> matching = new ArrayList<>();

        for (Task task : tasks) {
            assert task != null : "Task in list should not be null";
            if (task.toString().toLowerCase().contains(this.keyword.toLowerCase())) {
                matching.add(task);
            }
        }

        ui.showSearchResult(matching, keyword);
    }
}
