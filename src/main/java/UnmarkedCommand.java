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
     * @param task Task to be operated on
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(Task task, Ui ui) {
        task.markAsNotDone();
        ui.showTaskUnmarked(task);
    }
}
