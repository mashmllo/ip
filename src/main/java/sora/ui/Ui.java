package sora.ui;

import java.util.ArrayList;

import sora.task.Task;

/**
 * Handles all user interactions for Sora.
 * <p>
 * This class is responsible for displaying messages to the user.
 */
public class Ui {

    private final OutputHandler output;

    /**
     * Default constructor for CLI usage.
     * <p>
     * Sends all messages to the console.
     */
    public Ui() {
        this.output = new ConsoleOutput();
    }

    /**
     * Constructor for GUI or custom output destinations.
     *
     * @param output The {@link OutputHandler} implementation to use for rendering messages.
     */
    public Ui(OutputHandler output) {
        this.output = output;
    }

    /**
     * Displays a welcome message when the program start for CLI interface.
     */
    public void showCliGreetUser() {
        String logo = """
                '+.. + ..-. ..-.  *'.~~+'.  '  +* *''.~~+  .-. '  '''+ +   ++
                 * .-.   ) ). ) )      . . .-.  . .'  .-. (   )  .'.-. +.'  \s
                 .(   ) '-´  '-´ ' .-.  ' ( (    .-. ( ( . `-' .-.  ) ) .-. o
                +  `-' 'o *+'.  . (   )    `-'.'(   ) `-'.o   . ) )'-´ (   )'
                    o..o       +.. `-'  .''.. +  `-'     . .oo '-´ .o'. `-' o
                """;
        this.output.show(logo);
        this.output.show("Hey! I'm Sora");
        this.output.show("What would you like to do today?");
    }

    /**
     * Displays a welcome message when the program start for GUI interface.
     */
    public void showGuiGreetUser() {
        String greetings = "Hey! I'm Sora \n What would you like to do today?";
        this.output.show(greetings);
    }

    /**
     * Displays a newly added task.
     *
     * @param task  The task that has been added.
     * @param count The current number of tasks in the list.
     */
    public void showAddTask(Task task, int count) {
        String addTaskMsg = "Task added! Here's what I've recorded:\n" + " " + task
                + "\n" + "All set! You now have " + count + " in your list";

        this.output.show(addTaskMsg);
    }

    /**
     * Displays a confirmation that the task has been deleted.
     *
     * @param task  The task that has been removed.
     * @param count Current number of tasks in the list.
     */
    public void showDeletedTask(Task task, int count) {
        String deleteMsg = "Got it! Task removed:\n" + "  " + task
                + "\n" + "All set! You now have " + count + " in your list";

        this.output.show(deleteMsg);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Arraylist containing all the tasks.
     * @param count Current number of tasks in the list.
     */
    public void showTasks(ArrayList<Task> tasks, int count) {
        if (count == 0) {
            String errorMsg = "Hmm... it looks like you haven't add any tasks yet\n"
                    + "Why not start adding a new task?";
            showError(errorMsg);
            return;
        }

        StringBuilder sb = new StringBuilder("Here are your tasks: \n");
        for (int i = 0; i < count; i++) {
            sb.append(i + 1).append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
        this.output.show(sb.toString());
    }

    /**
     * Displays a task marked as complete.
     *
     * @param task Task that has been marked as complete.
     */
    public void showTaskMarked(Task task) {
        String markSuccessful = "Task Completed! You're making progress\n"
                + "  " + task;
        this.output.show(markSuccessful);
    }

    /**
     * Displays that the task has been marked as not complete.
     *
     * @param task Task that has been marked as not complete.
     */
    public void showTaskUnmarked(Task task) {
        String markUnsuccessful = "Okay! The task is still pending to be completed\n"
                + "  " + task;
        this.output.show(markUnsuccessful);
    }

    /**
     * Displays a list of matching tasks.
     *
     * @param tasks          Arraylist containing all the tasks.
     * @param matchingResult List of all matching tasks.
     * @param keyword        Keyword to search for.
     */
    public void showSearchResult(ArrayList<Task> tasks, ArrayList<Task> matchingResult, String keyword) {
        if (matchingResult.isEmpty()) {
            String errorMsg = "Hmm... No tasks found on " + keyword + " yet\n"
                    + "Try refining your search";
            showError(errorMsg);
            return;
        }

        StringBuilder results = new StringBuilder("Here are the tasks I found"
                + " matching " + keyword + ":\n");
        for (Task match : matchingResult) {
            int originalIndex = tasks.indexOf(match);
            results.append(originalIndex + 1).append(". ")
                    .append(match)
                    .append("\n");
        }
        this.output.show(results.toString());
    }

    /**
     * Displays an error message.
     *
     * @param message Error message to be displayed to the user
     */
    public void showError(String message) {
        this.output.showError(message);
    }

    /**
     * Displays a farewell message before the program exits.
     */
    public void farewellMessage() {
        this.output.show("Oh, leaving already? Hope you have a productive day!");
    }
}
