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
        } else if (cmd.toLowerCase().startsWith("mark ")) {
            markTask(cmd);
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
     * Mark a task as done based on the task number provided in the command
     * <p>
     * The command should be in the format "mark <task number>".
     * This method parses the task number from the command, validates it,
     * and updates corresponding task's status. If the input is invalid or
     * out of range, an error message will be displayed.
     *
     * @param cmd Full command entered by the user e.g. "mark 2"
     */
    private void markTask(String cmd) {
        try {
            int index = Integer.parseInt(cmd.split(" ")[1]) - 1;
            if (index < 0 || index >= taskCount) {
                ui.showError("Whoops! That task does not exist." +
                        "\nDouble-check the number and try again");
                return;
            }
            Task task = tasks[index];
            task.markAsDone();
            ui.showTaskMarked(task);
        } catch (NumberFormatException n) {
            ui.showError("Whoops! That number is not valid. " +
                    "\nCheck your task list and enter the correct number");
        }
    }
}
