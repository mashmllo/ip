package sora.ui;

import java.util.ArrayList;

import sora.task.Task;

/**
 * Handles all user interactions for the chatbot
 * <p>
 * This class is responsible for displaying messages to the user.
 */
public class Ui {

    /**
     * Display a welcome message when the program start
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
     * Display new task that has been added
     *
     * @param task  New tasks that has been added
     * @param count Number of tasks stored
     */
    public void showAddTask(Task task, int count) {
        printLine();
        System.out.println("Task added! Here's what I've recorded:");
        System.out.println(task);
        System.out.printf("All set! You now have %d in your list\n", count);
        printLine();
    }

    /**
     * Display confirmation that the task has been deleted
     *
     * @param task  New tasks that has been added
     * @param count Number of tasks stored
     */
    public void showDeletedTask(Task task, int count) {
        printLine();
        System.out.println("Got it! Task removed");
        System.out.println(" " + task);
        System.out.printf("All set! You now have %d in your list\n", count);
        printLine();
    }

    /**
     * Display the list of all tasks stored.
     * @param tasks Arraylist containing all the tasks
     * @param count Number of tasks stored
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
     * Display that the task has been marked as complete
     * @param task Task that has been marked as complete
     */
    public void showTaskMarked(Task task) {
        printLine();
        System.out.println("Task Completed! You're making progress");
        System.out.println(" " + task);
        printLine();
    }

    /**
     * Display that the task has been marked as complete
     * @param task Task that has been marked as complete
     */
    public void showTaskUnmarked(Task task) {
        printLine();
        System.out.println("Okay! The task is still pending to be completed");
        System.out.println(" " + task);
        printLine();
    }

    /**
     * Display error message
     * @param message Error message to be displayed to the user
     */
    public static void showError(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Display a farewell message before the program exits
     */
    public void farewellMessage() {
        printLine();
        System.out.println("Oh, leaving already? Hope you have a productive day!");
        printLine();
    }

    /**
     * Prints the horizontal divider on the console before and after each message
     */
    private static void printLine() {
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }

}
