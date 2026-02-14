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
     * @throws NullPointerException If {@code cmd} is {@code null}. This
     *                              exception is thrown to indicate improper
     *                              initialization of the object.
     * @throws SoraException If the command is incomplete or invalid.
     */
    public static Command parse(String cmd) throws SoraException {
        if (cmd == null) {
            throw new NullPointerException("Command string must not be null");
        }

        String lower = cmd.toLowerCase().trim();
        if (lower.isEmpty()) {
            throw new InvalidFormatException("Command string should not be empty");
        }

        String[] parts = lower.split("\\s+");
        CommandType keyword = CommandType.fromString(parts[0]);

        return switch (keyword) {
        case BYE
                -> new ExitCommand();

        case LIST
                -> new ListCommand();

        case MARK, UNMARK, DELETE
                -> parseIndexCommand(cmd, keyword.getKeyword());

        case ON
                -> parseFilter(cmd);

        case FIND
                -> parseSearch(cmd);

        case TODO, EVENT, DEADLINE
                -> parseAddTaskCommand(cmd, keyword.getKeyword());

        default
                -> throw new UnknownCommandException();
        };
    }

    /**
     * Parses a command that operates on an existing task index.
     *
     * @param cmd The full command entered by the user
     * @param keyword  The command keyword (e.g. "mark", "delete").
     * @return the corresponding {@link Command}.
     * @throws NullPointerException If either {@code cmd} pr {@code ui} is {@code null}.
     *                              This exception is thrown to indicate improper
     *                              initialization of the object.
     * @throws SoraException If the command format or index is invalid.
     */
    private static Command parseIndexCommand(String cmd, String keyword)
            throws SoraException {

        if (cmd == null) {
            throw new NullPointerException("Command string cannot be null");
        }

        if (keyword == null) {
            throw new UnknownCommandException();
        }

        int index = getIndex(cmd, keyword);

        CommandType type = CommandType.fromString(keyword);

        return switch (type) {
        case MARK
            -> new MarkCommand(index);
        case UNMARK
            -> new UnmarkCommand(index);
        case DELETE
            -> new DeleteCommand(index);
        default
            -> throw new UnknownCommandException();
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
     * @param keyword  The command keyword (e.g. "todo", "event", "deadline")
     * @return AddTaskCommand containing the parsed task
     * @throws SoraException if invalid or incomplete command is entered
     */
    private static Command parseAddTaskCommand(String cmd, String keyword)
            throws SoraException {
        Task task = parseTask(cmd, keyword);
        return new AddTaskCommand(task);
    }

    /**
     * Parses a command string into a {@link Task} object.
     *
     * @param cmd The full command entered by the user.
     * @param keyword  The command keyword (e.g. "todo", "event", "deadline").
     * @return The {@link Task} corresponding to the command.
     * @throws UnknownCommandException If the command is invalid or incomplete.
     */
    public static Task parseTask(String cmd, String keyword) throws SoraException {

        CommandType type = CommandType.fromString(keyword);

        return switch (type) {
        case TODO
                -> ToDo.parse(cmd);
        case DEADLINE
                -> Deadline.parse(cmd);
        case EVENT
                -> Event.parse(cmd);
        default
                -> throw new UnknownCommandException();
        };
    }

}
