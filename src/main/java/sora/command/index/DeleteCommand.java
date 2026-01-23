package sora.command.index;

import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Command to delete a task from task list
 */
public class DeleteCommand extends IndexCommand {

    private final int index;

    /**
     * Constructs a DeleteCommand
     *
     * @param index index of the task to be removed
     */
    public DeleteCommand(int index) {
        super(index);
        this.index = index;
    }

    /**
     * Removes the task from the task list
     * @param taskManager The task manager class
     * @param ui          The Ui class
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task deletedTask = taskManager.removeTask(this.index);

        if (deletedTask == null) {
            Ui.showError("Hmm.. something went wrong while removing the task" +
                    "\nPlease check the task number and try again");
        }
        ui.showDeletedTask(deletedTask, taskManager.getTaskCount());
    }
}
