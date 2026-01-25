package sora.command.index;

import sora.manager.TaskManager;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Command that mark a done as not done
 */
public class UnmarkedCommand extends IndexCommand {

    /**
     * Constructs an UnmarkedCommand
     * @param index index of the task
     */
    public UnmarkedCommand(int index) {
        super(index);
    }


    /**
     * Marks the task as not done and display confirmation
     * @param taskManager The task manager class
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task task = taskManager.unmarkTask(this.getIndex());
        ui.showTaskUnmarked(task);
    }
}
