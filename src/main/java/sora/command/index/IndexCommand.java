package sora.command.index;

import sora.command.Command;
import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Base abstract class for commands that requires a task index.
 * <p>
 * This class handles common validation logic, such as retrieving
 * a task and checking index bounds.
 */
public abstract class IndexCommand implements Command {

    private final int index;

    /**
     * Constructs an {@code IndexCommand}
     *
     * @param index Index of the task.
     */
    public IndexCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command by retrieving the task at the given index.
     *
     * @return The task index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Executes the command by retrieving the task at the given index
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException if task does not exist at the given index.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {

        Task task = taskManager.getTask(this.index);
        if (task == null) {
            throw new InvalidFormatException("Whoops! That task does not exist."
                    + "\nDouble-check the number and try again");
        }

        executeOnTask(taskManager, ui);
    }

    /**
     * Executes command-specific logic on the task.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     */
    protected abstract void executeOnTask(TaskManager taskManager, Ui ui);
}