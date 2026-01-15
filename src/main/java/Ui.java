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
