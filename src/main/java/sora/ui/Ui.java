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
        this.output.show("Hey! I'm Sora");
        this.output.show("What would you like to do today?");
    }

    /**
     * Displays a newly added task.
     *
     * @param task  The task that has been added.
     * @param count The current number of tasks in the list.
     */
    public void showAddTask(Task task, int count) {
        this.output.show("Task added! Here's what I've recorded:");
        this.output.show(task.toString());
        System.out.printf("All set! You now have %d in your list\n", count);
    }

    /**
     * Displays a confirmation that the task has been deleted
     *
     * @param task  The task that has been removed.
     * @param count Current number of tasks in the list.
     */
    public void showDeletedTask(Task task, int count) {
        this.output.show("Got it! Task removed");
        this.output.show(" " + task);
        System.out.printf("All set! You now have %d in your list\n", count);
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Arraylist containing all the tasks.
     * @param count Current number of tasks in the list.
     */
    public void showTasks(ArrayList<Task> tasks, int count) {
        if (count == 0) {
            this.output.show("Hmm... it looks like you haven't add any tasks yet");
            this.output.show("Why not start adding a new task?");
        } else {
            for (int i = 0; i < count; i++) {
                this.output.show((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    /**
     * Displays a task marked as complete.
     *
     * @param task Task that has been marked as complete.
     */
    public void showTaskMarked(Task task) {
        this.output.show("Task Completed! You're making progress");
        this.output.show(" " + task);
    }

    /**
     * Displays that the task has been marked as not complete.
     *
     * @param task Task that has been marked as not complete.
     */
    public void showTaskUnmarked(Task task) {
        this.output.show("Okay! The task is still pending to be completed");
        this.output.show(" " + task);
    }

    /**
     * Display a list of matching tasks.
     *
     * @param matchingResult List of all matching tasks.
     * @param keyword Keyword to search for.
     */
    public void showSearchResult(ArrayList<Task> matchingResult, String keyword) {
        if (matchingResult.isEmpty()) {
            this.output.show("Hmm... No tasks found on " + keyword + " yet");
            this.output.show("Try refining your search");
        } else {
            this.output.show("Here are the tasks I found matching " + keyword + ":");
            int count = 0;
            for (Task task: matchingResult) {
                this.output.show((count + 1) + ". " + task);
                count++;
            }
        }

    }

    /**
     * Display error message
     * <p>
     * @param message Error message to be displayed to the user
     */
    public void showError(String message) {
        this.output.show(message);
    }

    /**
     * Displays a farewell message before the program exits.
     */
    public void farewellMessage() {
        this.output.show("Oh, leaving already? Hope you have a productive day!");
    }
}
