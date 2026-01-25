package sora.command.index;

import sora.command.Command;
import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Base abstract class for commands that requires a task index
 * <p>
 * This class handles common validation logics such as retrieving
 * a task and checking index bounds
 */
public abstract class IndexCommand implements Command {

    private final int index;

    /**
     * Constructs an IndexCommand
     * @param index index of the task
     */
    public IndexCommand(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the task.
     *
     * @return The task index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Executes the command by retrieving the task at the given index
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     * @throws SoraException if task does not exist
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {

        Task task = taskManager.getTask(index);
        if (task == null) {
            throw new InvalidFormatException("Whoops! That task does not exist."
                    + "\nDouble-check the number and try again");
        }

        executeOnTask(taskManager, ui);
    }

    /**
     * Executes command-specific logic on the task
     * @param taskManager The task manager class
     * @param ui          The user interface class
     */
    protected abstract void executeOnTask(TaskManager taskManager, Ui ui);
}
