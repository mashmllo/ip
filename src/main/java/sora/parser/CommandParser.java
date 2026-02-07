package sora.parser;

import sora.command.AddTaskCommand;
import sora.command.Command;
import sora.command.ExitCommand;
import sora.command.FindCommand;
import sora.command.ListCommand;
import sora.command.OnCommand;
import sora.command.index.DeleteCommand;
import sora.command.index.MarkCommand;
import sora.command.index.UnmarkCommand;
import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.exception.UnknownCommandException;
import sora.task.Deadline;
import sora.task.Event;
import sora.task.Task;
import sora.task.ToDo;

/**
 * Parses user commands to its respective {@link Command} implementations.
 * <p>
 *  Responsible for validating and parsing user input
 *  and instantiates the appropriate {@link Command} object.
 */
public class CommandParser {

    /**
     * Parses user input and returns the corresponding {@link Command}.
     *
     * @param cmd The full command entered by the user.
     * @return The {@link Command} to be executed.
     * @throws SoraException If the command is incomplete or invalid.
     */
    public static Command parse(String cmd) throws SoraException {
        assert cmd != null : "Command string must not be null";

        String lower = cmd.toLowerCase().trim();
        assert !lower.isEmpty() : "Command string should not be empty";

        String[] parts = lower.split("\\s+");
        String keyword = parts[0];

        return switch (keyword) {
        case "bye"
                -> new ExitCommand();

        case "list"
                -> new ListCommand();

        case "mark", "unmark", "delete"
                -> parseIndexCommand(cmd, keyword);

        case "on"
                -> parseFilter(cmd);

        case "find"
                -> parseSearch(cmd);

        default
                -> parseAddTaskCommand(cmd);
        };
    }

    /**
     * Parses a command that operates on an existing task index.
     *
     * @param cmd The full command entered by the user
     * @param keyword  The command keyword (e.g. "mark", "delete").
     * @return the corresponding {@link Command}.
     * @throws SoraException If the command format or index is invalid.
     */
    private static Command parseIndexCommand(String cmd, String keyword)
            throws SoraException {

        assert cmd != null : "Command string cannot be null";
        assert keyword != null : "Command keyword cannot be null";

        int index = getIndex(cmd, keyword);

        return switch (keyword) {
        case "mark":
            yield new MarkCommand(index);
        case "unmark":
            yield new UnmarkCommand(index);
        case "delete":
            yield new DeleteCommand(index);
        default:
            throw new UnknownCommandException();
        };
    }

    /**
     * Extracts index from a command that requires a task number.
     *
     * @param cmd The full command entered by the user.
     * @param keyword  The command keyword (e.g. "mark", "delete").
     * @return  The zero-based index of the task.
     * @throws InvalidFormatException If the command format or task number is invalid.
     */
    private static int getIndex(String cmd, String keyword)
            throws InvalidFormatException {

        assert cmd != null : "Command string cannot be null";
        assert keyword != null : "Command keyword cannot be null";

        String[] parts = cmd.split(" ");
        if (parts.length != 2) {
            throw new InvalidFormatException("Hmm... I need a task number to proceed"
                    + "\n Try something like: " + keyword + " 3");
        }

        int index;
        try {
            index = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException numberException) {
            throw new InvalidFormatException("Whoops! That number is not valid."
                    + "\nCheck your task list and enter the correct number");
        }
        return index;
    }

    /**
     * Parses the filter command for tasks occurring on a specific date.
     *
     * @param cmd The full command entered by the user.
     * @return The corresponding {@link Command}.
     * @throws InvalidFormatException If the date string is invalid.
     */
    private static Command parseFilter(String cmd)
            throws InvalidFormatException {

        assert cmd.toLowerCase().startsWith("on")
                : "Filter command must start with on";

        String target = cmd.substring(2).trim();
        if (target.isEmpty()) {
            throw new InvalidFormatException("Oops! Invalid date format. "
                    + "\n Use `" + DateInputType.DATE_INPUT_PATTERN + "` "
                    + "or `" + DateInputType.DATETIME_INPUT_PATTERN + "`");
        }

        return new OnCommand(target);
    }

    /**
     * Parses the FindCommand to search for task by name.
     *
     * @param cmd The full command entered by the user.
     * @return The corresponding FindCommand.
     * @throws InvalidFormatException If string to search is empty.
     */
    private static Command parseSearch(String cmd)
            throws InvalidFormatException {

        assert cmd.toLowerCase().startsWith("find")
                : "Search command must start with find";
        String target = cmd.substring(4).trim();
        if (target.isEmpty()) {
            throw new InvalidFormatException("Oops! Keyword cannot be empty. "
                    + "\n Use find <keyword>");
        }

        return new FindCommand(target);
    }

    /**
     * Parse task creation command.
     *
     * @param cmd Full command entered by the user
     * @return AddTaskCommand containing the parsed task
     * @throws SoraException if invalid or incomplete command is entered
     */
    private static Command parseAddTaskCommand(String cmd) throws SoraException {
        Task task = parseTask(cmd);
        return new AddTaskCommand(task);
    }

    /**
     * Parses a command string into a {@link Task} object.
     *
     * @param cmd The full command entered by the user.
     * @return The {@link Task} corresponding to the command.
     * @throws UnknownCommandException If the command is invalid or incomplete.
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
