package sora.task;

import sora.exception.InvalidFormatException;
import sora.parser.ParsedDateTime;

/**
 * Represents a Deadline task for Sora.
 * <p>
 *  A Deadline task are tasks that needs to be completed by a given date/time.
 * This class extends the base {@link Task} class
 * and adds a {@code by} field to store the deadline.
 * The marks task type as [D].
 */
public class Deadline extends Task {

    private final ParsedDateTime by;

    /**
     * Constructor of a new {@code Deadline} task.
     * @param name Name of the task.
     * @param by   Due date of the task as a
     *             {@link ParsedDateTime}.
     */
    public Deadline(String name, ParsedDateTime by) {
        super(name);
        this.by = by;
    }

    /**
     * Returns the deadline of this task.
     *
     * @return The deadline of the task as a
     *         {@link ParsedDateTime}.
     */
    public ParsedDateTime getDeadline() {
        return this.by;
    }

    /**
     * Returns the string representation of the Deadline task for display.
     * @return The formatted string e.g. "[D][X] read book (by: Jan 22 2026)"
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

    /**
     * Returns the string representation of the Deadline task for storage in a file.
     * @return The formatted string e.g. "D | 1 | read book | 2026-01-22"
     */
    @Override
    public String toStorageString() {
        return "D " + super.toStorageString() + " | " + this.by.toStorageString();
    }

    /**
     * Parses a command string to create a new {@link Deadline} task.
     * <p>
     * Command to follow the following format:
     * <pre>deadline &lt; name of task&gt; /by &lt;deadline&gt;</pre>
     * <p>
     * Both the name of the task and the deadline must be provided,
     * otherwise {@link InvalidFormatException} is thrown
     * and the task is not being added into the list.
     *
     * @param cmd The full command entered by the user
     *            e.g. "deadline submit report /by 2026-01-22 23:59"
     * @return A new {@link Deadline} task object.
     * @throws InvalidFormatException If the task name or deadline is missing or invalid.
     */
    public static Deadline parse(String cmd)
            throws InvalidFormatException {
        String[] parts = cmd.substring(8).split(" /by ", 2);
        if (parts.length < 2
                || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty()) {
            throw new InvalidFormatException("Oops! Deadline requires /by "
                    + "and name");
        }
        return new Deadline(parts[0].trim(), ParsedDateTime.dateTimeParser(parts[1].trim()));
    }
}
