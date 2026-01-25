package sora.command.index;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that delete a task from task list.
 */
public class DeleteCommand extends IndexCommand {

    private final int index;

    /**
     * Constructs a {@code DeleteCommand}.
     *
     * @param index Index of the task to be removed.
     */
    public DeleteCommand(int index) {
        super(index);
        this.index = index;
    }

    /**
     * Removes the task from the task list based on the given index.
     * Displays an error message if the task cannot be found.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task deletedTask = taskManager.removeTask(this.index);

        if (deletedTask == null) {
            Ui.showError("Hmm.. something went wrong while removing the task"
                    + "\nPlease check the task number and try again");
        }
        ui.showDeletedTask(deletedTask, taskManager.getTaskCount());
    }
}