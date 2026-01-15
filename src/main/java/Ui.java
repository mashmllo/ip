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
     * Echos the command back to the user.
     *
     * @param cmd command to echo.
     */
    public void echo(String cmd) {
        printLine();
        System.out.println(cmd);
        printLine();
    }

    /**
     * Display new task that has been added
     *
     * @param task New tasks that has been added
     */
    public void showAddTask(String task) {
        printLine();
        System.out.println("added: " + task);
        printLine();
    }

    /**
     * Display the list of all tasks stored.
     * @param tasks Array containing all the tasks
     * @param count Number of tasks stored
     */
    public void showTasks(Task[] tasks, int count) {
        printLine();
        if (count == 0) {
            System.out.println("Hmm... it looks like you haven't add any tasks yet");
            System.out.println("Why not start adding a new task?");
        } else {
            for (int i = 0; i < count; i++) {
                System.out.println((i+1) + ". " + tasks[i]);
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
    public void showError(String message) {
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
    private void printLine() {
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }

}
