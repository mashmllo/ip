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
     * Prints the horizontal divider on the console before and after each message
     */
    private void printLine() {
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }

}
