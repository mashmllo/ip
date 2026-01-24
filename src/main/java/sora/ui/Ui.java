package sora.ui;

import sora.task.Task;

import java.util.ArrayList;

/**
 * Handles all user interactions for Sora.
 * <p>
 * This class is responsible for displaying messages to the user.
 */
public class Ui {

    /**
     * Displays a welcome message when the program start.
     */
    public void greetUser() {
        String logo = """
                '+.. + ..-. ..-.  *'.~~+'.  '  +* *''.~~+  .-. '  '''+ +   ++
                 * .-.   ) ). ) )      . . .-.  . .'  .-. (   )  .'.-. +.'  \s
                 .(   ) '-´  '-´ ' .-.  ' ( (    .-. ( ( . `-' .-.  ) ) .-. o
                +  `-' 'o *+'.  . (   )    `-'.'(   ) `-'.o   . ) )'-´ (   )'
                    o..o       +.. `-'  .''.. +  `-'     . .oo '-´ .o'. `-' o
                
                """;
        System.out.print("\n" + logo);
        printLine();
        System.out.println("Hey! I'm Sora");
        System.out.println("What would you like to do today?");
        printLine();
    }

    /**
     * Displays a newly added task.
     *
     * @param task  The task that has been added.
     * @param count The current number of tasks in the list.
     */
    public void showAddTask(Task task, int count) {
        printLine();
        System.out.println("Task added! Here's what I've recorded:");
        System.out.println(task);
        System.out.printf("All set! You now have %d in your list\n", count);
        printLine();
    }

    /**
     * Displays a confirmation that the task has been deleted
     *
     * @param task  The task that has been removed.
     * @param count Current number of tasks in the list.
     */
    public void showDeletedTask(Task task, int count) {
        printLine();
        System.out.println("Got it! Task removed");
        System.out.println(" " + task);
        System.out.printf("All set! You now have %d in your list\n", count);
        printLine();
    }

    /**
     * Displays all tasks in the task list.
     *
     * @param tasks Arraylist containing all the tasks.
     * @param count Current number of tasks in the list.
     */
    public void showTasks(ArrayList<Task> tasks, int count) {
        printLine();
        if (count == 0) {
            System.out.println("Hmm... it looks like you haven't add any tasks yet");
            System.out.println("Why not start adding a new task?");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println((i+1) + ". " + tasks.get(i));
            }
        }
        printLine();
    }

    /**
     * Displays a task marked as complete.
     *
     * @param task Task that has been marked as complete.
     */
    public void showTaskMarked(Task task) {
        printLine();
        System.out.println("Task Completed! You're making progress");
        System.out.println(" " + task);
        printLine();
    }

    /**
     * Displays that the task has been marked as not complete.
     *
     * @param task Task that has been marked as not complete.
     */
    public void showTaskUnmarked(Task task) {
        printLine();
        System.out.println("Okay! The task is still pending to be completed");
        System.out.println(" " + task);
        printLine();
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed to the user.
     */
    public static void showError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Displays a farewell message before the program exits.
     */
    public void farewellMessage() {
        printLine();
        System.out.println("Oh, leaving already? Hope you have a productive day!");
        printLine();
    }

    /**
     * Prints the horizontal divider for readability.
     */
    private static void printLine() {
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }

}
