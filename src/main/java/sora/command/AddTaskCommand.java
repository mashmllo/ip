package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represent a command that allows user to add tasks to the task list.
 */
public class AddTaskCommand implements Command {

    private final Task task;

    /**
     * Constructs an {@code AddTaskCommand}.
     *
     * @param task Task to be added.
     */
    public AddTaskCommand(Task task) {
        assert task != null : "Task to be added should not be null";
        this.task = task;
    }

    /**
     * Executes the command by adding the task to the task list
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException If task cannot be added.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        assert taskManager != null : "TaskManager object should not be null";
        assert ui != null : "Ui object should not be null";

        taskManager.addTask(this.task);
        ui.showAddTask(this.task, taskManager.getTaskCount());
    }
}
