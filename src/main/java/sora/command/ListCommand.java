package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.ui.Ui;

/**
 * Represents a command that displays all tasks in the task list.
 */
public class ListCommand implements Command {

    /**
     * Executes the list command and display all tasks in the tasks list.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException If either {@code taskManager} or {@code ui} is {@code null}.
     *                       This exception is thrown to indicate improper initialization of
     *                       the object.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        if (taskManager == null) {
            throw new NullPointerException("TaskManager object should not be null");
        }

        if (ui == null) {
            throw new NullPointerException("Ui object should not be null");
        }

        ui.showTasks(taskManager.getTasks(), taskManager.getTaskCount());
    }
}
