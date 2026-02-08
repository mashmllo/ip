package sora.command.index;

import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that mark a task as done
 */
public class MarkCommand extends IndexCommand {

    /**
     * Constructs a {@code MarkCommand}
     *
     * @param index Index of the task to be marked as complete.
     */
    public MarkCommand(int index) {
        super(index);
    }

    /**
     * Marks the task as done and display a confirmation message.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task task = taskManager.markTask(this.getIndex());
        ui.showTaskMarked(task);
    }
}

