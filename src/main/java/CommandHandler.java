import java.util.Scanner;

/**
 * Handles user interactions.
 * <p>
 * This class is responsible for managing the main workflow of Sora,
 * including greeting the user, processing commands, and displaying farewell message.
 */
public class CommandHandler {

    private final Ui ui;
    private final Scanner scanner;
    private final TaskManager taskManager;
    private boolean isRunning;


    public CommandHandler() {
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);
        this.taskManager = new TaskManager();
        this.isRunning = true;
    }

    /**
     * Executes the command handler's main workflow
     * <p>
     * Serve as an entry point for handling user interactions or processing commands.
     */
    public void run() {
        ui.greetUser();

        while (isRunning) {
            String cmd = scanner.nextLine().strip();
            try {
                handleCommand(cmd);
            } catch (SoraException soraException) {
                ui.showError(soraException.getMessage());
            }
        }

        ui.farewellMessage();
        scanner.close();
    }

    /**
     * Process the command entered by user
     *
     * @param cmd command entered by user
     */
    private void handleCommand(String cmd) {
        if (cmd.equalsIgnoreCase("bye")) {
            isRunning = false;
        } else if (cmd.equalsIgnoreCase("list")) {
            ui.showTasks(taskManager.getTasks(), taskManager.getTaskCount());
        } else if (cmd.toLowerCase().startsWith("mark")
                || cmd.toLowerCase().startsWith("unmark")) {
            updateTaskStatus(cmd);
        } else {
            addTask(cmd);
        }
    }

    /**
     * Adds a new task to array
     *
     * @param cmd Full command entered by the user
     * @throws SoraException if the command is invalid
     */
    private void addTask(String cmd) throws SoraException {
        Task task = Parser.parseTask(cmd); // Parser throw SoraException subclasses
        taskManager.addTask(task);
        ui.showAddTask(task, taskManager.getTaskCount());
    }


    /**
     * Update the status of a task based on the command
     * <p>
     * The command should be in the following format:
     *  - "mark <task number>" to mark a task as done
     *  - "unmark <task number>" to mark a task as not done
     * <p>
     * This method parses the task number from the command, validates it,
     * and updates the task's status. Errors are displayed if the input number is
     * invalid or out of range.
     *
     * @param cmd Full command entered by the user e.g. "mark 2", "unmark 3"
     * @throws SoraException if invalid command is entered
     *                       or task number is out of range
     */
    private void updateTaskStatus(String cmd)
            throws SoraException{
        String[] parts = cmd.split(" ");
        if (parts.length != 2) {
            throw new InvalidFormatException("Hmm... I need a task number to proceed"
                    +"\n Try something like: mark 2, unmark 3");
        }

        int index;
        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidFormatException("Whoops! That number is not valid. " +
                    "\nCheck your task list and enter the correct number");
        }

        Task task = taskManager.getTask(index);
        if (task == null) {
            throw new InvalidFormatException("Whoops! That task does not exist." +
                    "\nDouble-check the number and try again");
        }

        String keyword = parts[0].toLowerCase();
        if (keyword.equals("mark")) {
            task.markAsDone();
            ui.showTaskMarked(task);
        } else if (keyword.equals("unmark")) {
            task.markAsNotDone();
            ui.showTaskUnmarked(task);
        } else {
            throw new UnknownCommandException();
        }

    }
}
