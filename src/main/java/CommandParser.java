/**
 * Parses user commands to its respective command based on user input
 * <p>
 *  This class is responsible for validating and parsing user input
 *  and instantiate the appropriate Command implementation
 */
public class CommandParser {

    /**
     * Parses user input and returns the corresponding Command
     *
     * @param cmd Full command entered by the user
     * @return Command to be executed
     * @throws SoraException If incomplete or invalid command is entered
     */
    public static Command parse(String cmd) throws SoraException {
        String lower = cmd.toLowerCase().trim();

        if (lower.startsWith("bye")) {
            return new ExitCommand();
        } else if (lower.startsWith("list")) {
            return new ListCommand();
        } else if (lower.startsWith("mark")) {
            return parseIndexCommand(cmd, "mark");
        } else if (lower.startsWith("unmark")) {
            return parseIndexCommand(cmd, "unmark");
        } else if (lower.startsWith("delete")) {
            return parseIndexCommand(cmd, "delete");
        } else {
            return parseAddTaskCommand(cmd);
        }
    }

    /**
     * Parses command that operates on an existing task index
     * @param cmd Full command entered by the user
     * @param keyword  Command keyword
     * @return the corresponding task
     * @throws SoraException if the command format or index is invalid
     */
    private static Command parseIndexCommand(String cmd, String keyword)
            throws SoraException {

        String[] parts = cmd.split(" ");
        if (parts.length != 2) {
            throw new InvalidFormatException("Hmm... I need a task number to proceed"
                    +"\n Try something like: " + keyword + " 3");
        }

        int index;
        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidFormatException("Whoops! That number is not valid. " +
                    "\nCheck your task list and enter the correct number");
        }

        if (keyword.equals("mark")) {
            return new MarkCommand(index);
        } else if (keyword.equals("unmark")) {
            return new UnmarkedCommand(index);
        } else if (keyword.equals("delete")) {
            return new DeleteCommand(index);
        } else {
            throw new UnknownCommandException();
        }
    }

    /**
     * Parse task creation command
     * @param cmd Full command entered by the user
     * @return AddTaskCommand containing the parsed task
     * @throws SoraException if invalid or incomplete command is entered
     */
    private static Command parseAddTaskCommand(String cmd) throws SoraException {
        Task task = parseTask(cmd);
        return new AddTaskCommand(task);
    }

    /**
     * Parses a command string into a Task object
     *
     * @param cmd Full command entered by the user
     * @return A Task object corresponding to the command entered by the user
     * @throws UnknownCommandException if an invalid or incomplete command is entered
     */
    public static Task parseTask(String cmd) throws SoraException {

        String cmdLowercase = cmd.toLowerCase();

        if (cmdLowercase.startsWith(("todo"))) {
            return parseTodo(cmd);
        } else if (cmdLowercase.startsWith("deadline")) {
            return parseDeadline(cmd);
        } else if (cmdLowercase.startsWith("event")) {
            return parseEvent(cmd);
        } else {
            throw new UnknownCommandException();
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
     * @throws InvalidFormatException if task name is missing
     */
    private static ToDo parseTodo(String cmd)
            throws InvalidFormatException {
        String taskName = cmd.substring(4).trim();

        if (taskName.isEmpty()) {
           throw new InvalidFormatException("Oops! The task name is missing ");
        }

        return new ToDo(taskName);
    }

    /**
     * Creates and adds a Deadline task to the task list
     * <p>
     * Command to follow the following format:
     *      deadline <name of task> /by <deadline>
     * <p>
     * Both the name of the task and the deadline must be provided,
     * otherwise an error message is shown and the task is not being added into the
     * list.
     *
     * @param cmd Full command entered by the user
     *            e.g. "deadline submit report /by 11/10/2025 5pm"
     * @return A Deadline task object
     * @throws InvalidFormatException if task name or deadline is missing
     */
    private static Deadline parseDeadline(String cmd)
    throws InvalidFormatException {
        String[] parts = cmd.substring(8).split(" /by ", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("Oops! Deadline requires /by " +
                    "and name");
        }
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    /**
     * Creates and adds an Event task to the task list
     * <p>
     * Command to follow the following format:
     *      event <name of task> /from <start time> /to <end time>
     * <p>
     * All 3 fields, name of task, start time and end time, must be provided,
     * otherwise an error message is shown and the task is not being added into the
     * list.
     *
     * @param cmd Full command entered by the user
     *            e.g. "event project meeting /from Mon 2pm /to 4pm"
     * @return An Event task object
     * @throws InvalidFormatException if task name, start time, or end time
     *                                  is missing
     */
    private static Event parseEvent(String cmd)
            throws InvalidFormatException {
        String[] parts = cmd.substring(5)
                .split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new InvalidFormatException("Oops! Event requires /from," +
                    " /to and name");
        }
        return new Event(parts[0].trim(), parts[1].trim(),  parts[2].trim());
    }
}
