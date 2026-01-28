package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

import java.util.ArrayList;

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

        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Task> matching = new ArrayList<>();

        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(this.keyword.toLowerCase())) {
                matching.add(task);
            }
        }

        ui.showSearchResult(matching, keyword);
    }
}
