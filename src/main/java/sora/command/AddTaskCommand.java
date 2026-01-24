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
        taskManager.addTask(task);
        ui.showAddTask(task, taskManager.getTaskCount());
    }
}
