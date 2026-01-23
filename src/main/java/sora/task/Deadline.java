package sora.task;

import sora.exception.InvalidFormatException;
import sora.parser.ParsedDateTime;

/**
 * Deadline task for Sora
 * <p>
 *  A Deadline task are tasks that needs to be completed by a given date/time.
 * This class extends the base Task class and adds a "by" field to store the deadline.
 * The marks task type as [D].
 */
public class Deadline extends Task {

    private final ParsedDateTime by;

    /**
     * Constructor of a new Deadline task
     * @param name Name of the task
     * @param by   Due date of the task
     */
    public Deadline(String name, ParsedDateTime by) {
        super(name);
        this.by = by;
    }


    /**
     * Retrieve the deadline of the task
     *
     * @return deadline of the task
     */
    public ParsedDateTime deadline() {
        return by;
    }

    /**
     * Return the string representation of the Deadline task.
     * @return formatted string e.g. "[D][X] read book (by: Jan 22 2026)"
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Return string representation used for file storage for Deadline task.
     * @return formatted string e.g. "D | 1 | read book | 2026-01-22"
     */
    @Override
    public String toStorageString() {
        return "D " + super.toStorageString() + " | " + by.toStorageString();
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
     *            e.g. "deadline submit report /by 2026-01-22 23:59"
     * @return A Deadline task object
     * @throws InvalidFormatException if task name or deadline is missing
     */
    public static Deadline parse(String cmd)
            throws InvalidFormatException {
        String[] parts = cmd.substring(8).split(" /by ", 2);
        if (parts.length < 2
                || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("Oops! Deadline requires /by " +
                    "and name");
        }
        return new Deadline(parts[0].trim(), ParsedDateTime.dateTimeParser(parts[1].trim()));
    }
}
