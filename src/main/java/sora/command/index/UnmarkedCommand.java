package sora.command.index;

import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command that mark a completed task as not done.
 */
public class UnmarkedCommand extends IndexCommand {

    /**
     * Constructs an {@code UnmarkedCommand}
     *
     * @param index Index of the task to be unmarked.
     */
    public UnmarkedCommand(int index) {
        super(index);
    }


    /**
     * Marks the task as not done and display a confirmation message.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task task = taskManager.unmarkTask(this.getIndex());
        ui.showTaskUnmarked(task);
    }
}

