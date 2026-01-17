/**
 * Command that mark a task as done
 */
public class MarkCommand extends IndexCommand {

    /**
     * Constructs a MarkCommand
     * @param index index of the task to be mark as complete
     */
    public MarkCommand(int index) {
        super(index);
    }

    /**
     * Marks the task as done and display confirmation
     * @param task Task to be operated on
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(Task task, Ui ui) {
        task.markAsDone();
        ui.showTaskMarked(task);
    }
}
