package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * CCommand that allows user to add tasks to the task list
 */
public class AddTaskCommand implements Command {

    private final Task task;

    /**
     * Constructs an AddTaskCommand
     * @param task Task to be added
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the command by adding the task to the task list
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     * @throws SoraException if invalid or incomplete command is entered
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        taskManager.addTask(task);
        ui.showAddTask(task, taskManager.getTaskCount());
    }
}
