/**
 * Command that mark a task as done
 */
public class MarkCommand extends IndexCommand {

    private final int index;
    /**
     * Constructs a MarkCommand
     * @param index index of the task to be mark as complete
     */
    public MarkCommand(int index) {
        super(index);
        this.index = index;
    }

    /**
     * Marks the task as done and display confirmation
     * @param taskManager The task manager class
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task task = taskManager.markTask(this.index);
        ui.showTaskMarked(task);
    }
}
