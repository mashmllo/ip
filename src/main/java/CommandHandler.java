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
    private boolean isRunning;

    private final Task[] tasks = new Task[100];
    private int taskCount = 0;

    public CommandHandler() {
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);
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
            handleCommand(cmd);
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
            ui.showTasks(tasks, taskCount);
        } else if (cmd.toLowerCase().startsWith("mark ")
                || cmd.toLowerCase().startsWith("unmark")) {
            updateTaskStatus(cmd);
        } else {
            addTask(cmd);
        }
    }

    /**
     * Adds a new task to array
     *
     * @param task New task to be added
     */
    private void addTask(String task) {
        tasks[taskCount] = new Task(task);
        taskCount++;
        ui.showAddTask(task);
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
     */
    private void updateTaskStatus(String cmd) {
        String[] parts = cmd.split(" ");
        if (parts.length != 2) {
            ui.showError("Hmm... I need a task number to proceed" +
                    "\n Try something like: mark 2, unmark 3");
            return;
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskCount) {
                ui.showError("Whoops! That task does not exist." +
                        "\nDouble-check the number and try again");
                return;
            }
            Task task = tasks[index];
            if (parts[0].equalsIgnoreCase("mark")) {
                task.markAsDone();
                ui.showTaskMarked(task);
            } else if (parts[0].equalsIgnoreCase("unmark")) {
                task.markAsNotDone();
                ui.showTaskUnmarked(task);
            } else {
                ui.showError("Hmm... I don't recognize that command" +
                        "\n Try using: mark <task number> or unmark <task number>");
            }

        } catch (NumberFormatException n) {
            ui.showError("Whoops! That number is not valid. " +
                    "\nCheck your task list and enter the correct number");
        }
    }
}
