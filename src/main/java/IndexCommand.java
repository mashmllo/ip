/**
 * Base abstract class for commands that requires a task index
 * <p>
 * This class handles common validation logics such as retrieving
 * a task and checking index bounds
 */
public abstract class IndexCommand implements Command {

    private final int index;

    /**
     * Constructs an IndexCommand
     * @param index index of the task
     */
    public IndexCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the command by retrieving the task at the given index
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     * @throws SoraException if task does not exist
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {

        Task task = taskManager.getTask(index);
        if (task == null) {
            throw new InvalidFormatException("Whoops! That task does not exist." +
                    "\nDouble-check the number and try again");
        }

        executeOnTask(task, ui);
    }

    /**
     * Executes command-specific logic on the task
     * @param task Task to be operated on
     * @param ui User interface for displaying output
     */
    protected abstract void executeOnTask(Task task, Ui ui);
}
