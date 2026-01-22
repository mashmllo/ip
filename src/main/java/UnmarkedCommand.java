/**
 * Command that mark a done as not done
 */
public class UnmarkedCommand extends IndexCommand {

    private final int index;
    /**
     * Constructs an UnmarkedCommand
     * @param index index of the task
     */
    public UnmarkedCommand(int index) {
        super(index);
        this.index = index;
    }


    /**
     * Marks the task as not done and display confirmation
     * @param taskManager The task manager class
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(TaskManager taskManager, Ui ui) {
        Task task = taskManager.unmarkTask(this.index);
        ui.showTaskUnmarked(task);
    }
}
