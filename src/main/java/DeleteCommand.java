/**
 * Command to delete a task from task list
 */
public class DeleteCommand extends IndexCommand {

    private final int index;
    private final TaskManager taskManager;

    /**
     * Constructs a DeleteCommand
     *
     * @param index index of the task to be removed
     * @param taskManager the task manager class
     */
    public DeleteCommand(int index, TaskManager taskManager) {
        super(index);
        this.index = index;
        this.taskManager = taskManager;
    }

    /**
     * Removes the task from the task list
     * @param task Task to be operated on
     * @param ui User interface for displaying output
     */
    @Override
    protected void executeOnTask(Task task, Ui ui) {
        Task deletedTask = taskManager.removeTask(this.index);

        if (deletedTask == null) {
            Ui.showError("Hmm.. something went wrong while removing the task" +
                    "\nPlease check the task number and try again");
        }
        ui.showDeletedTask(task, taskManager.getTaskCount());
    }
}
