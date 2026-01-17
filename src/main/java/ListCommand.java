/**
 * Command that displays all tasks in the task list
 */
public class ListCommand implements Command {

    /**
     * Executes the list command and display all tasks in the tasks list
     * <p>
     * Note: This implementation does not throw any SoraException
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        ui.showTasks(taskManager.getTasks(), taskManager.getTaskCount());
    }
}
