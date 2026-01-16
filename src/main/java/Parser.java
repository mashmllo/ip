/**
 * Parses user commands and create Task object based on command prefix.
 * <p>
 *  This class is responsible for validating user input and throws exceptions
 *  if the command format is invalid or if required information is missing.
 */
public class Parser {

    /**
     * Parses a command string into a Task object
     *
     * @param cmd Full command entered by the user
     * @return A Task object corresponding to the command entered by the user
     * @throws IllegalArgumentException if the command format is invalid
     */
    public static Task parseTask(String cmd) {
        if (cmd.toLowerCase().startsWith(("todo"))) {
            return parseTodo(cmd);
        } else {
            throw new IllegalArgumentException("Oops! Unknown task type" +
                    "\n Make sure you're using a valid task type");
        }
    }

    /**
     * Creates and adds a Todo task to the task list
     * <p>
     * Command to follow the following format:
     *      todo <name of task>
     * <p>
     * If name of task is missing or empty, an error message is shown and task is
     * not being added into the list
     *
     * @param cmd Full command entered by the user e.g. "todo read book"
     * @return A ToDo task object
     * @throws IllegalArgumentException if task name is missing
     */
    private static ToDo parseTodo(String cmd) throws IllegalArgumentException {
        String taskName = cmd.substring(4).trim();

        if (taskName.isEmpty()) {
           throw new IllegalArgumentException("Oops! The task name is missing ");
        }

        return new ToDo(taskName);
    }

}
