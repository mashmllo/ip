package sora.parser;

import sora.command.Command;
import sora.command.ExitCommand;
import sora.command.ListCommand;
import sora.command.AddTaskCommand;
import sora.command.OnCommand;
import sora.command.index.DeleteCommand;
import sora.command.index.MarkCommand;
import sora.command.index.UnmarkedCommand;
import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.exception.UnknownCommandException;
import sora.task.Deadline;
import sora.task.Event;
import sora.task.Task;
import sora.task.ToDo;

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
        } else if (lower.startsWith("on")) {
            return parseSearch(cmd);
        }else {
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

        int index = getIndex(cmd, keyword);

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
     * Extracts index from the command that requires task number
     *
     * @param cmd      Full command string entered by the user
     * @param keyword  Command keyword
     * @return  Zero-based index of the task
     * @throws InvalidFormatException if command format or task number is invalid
     */
    private static int getIndex(String cmd, String keyword)
            throws InvalidFormatException {
        String[] parts = cmd.split(" ");
        if (parts.length != 2) {
            throw new InvalidFormatException("Hmm... I need a task number to proceed"
                    +"\n Try something like: " + keyword + " 3");
        }

        int index;
        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException numberException) {
            throw new InvalidFormatException("Whoops! That number is not valid." +
                    "\nCheck your task list and enter the correct number");
        }
        return index;
    }

    /**
     * Parses the search command for tasks occurring on a specific date
     *
     * @param cmd Full command entered by the user
     * @return the corresponding task
     * @throws InvalidFormatException if date string is not valid
     */
    private static Command parseSearch(String cmd)
            throws InvalidFormatException {
        String target = cmd.substring(2).trim();
        if (target.isEmpty()) {
            throw new InvalidFormatException("Oops! Invalid date format. " +
                    "\n Use `" + DateInputType.DATE_INPUT_PATTERN +"` " +
                    "or `" + DateInputType.DATETIME_INPUT_PATTERN +"`");
        }

        return new OnCommand(target);
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
            return ToDo.parse(cmd);
        } else if (cmdLowercase.startsWith("deadline")) {
            return Deadline.parse(cmd);
        } else if (cmdLowercase.startsWith("event")) {
            return Event.parse(cmd);
        } else {
            throw new UnknownCommandException();
        }
    }


}
