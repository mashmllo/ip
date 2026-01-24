package sora.task;

import sora.exception.InvalidFormatException;
import sora.parser.ParsedDateTime;

/**
 * Represents an Event task in Sora.
 * <p>
 *  An Event task are tasks that occurs during a specific time period.
 * This class extends the base {@link Task} class
 * and adds {@code from} and {@code to} fields to
 * store the event duration.
 * Marks the task type as [E].
 */
public class Event extends Task {

    private final ParsedDateTime from;
    private final ParsedDateTime to;

    /**
     * Constructs a new Event class with a name, start and end time.
     * @param name  The name of the event.
     * @param from  The start date/time of the event as a {@link ParsedDateTime}.
     * @param to    The end date/time of the event as a {@link ParsedDateTime}.
     */
    public Event(String name, ParsedDateTime from, ParsedDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }


    /**
     * Returns the start date of the event.
     *
     * @return The start date of the event as a {@link ParsedDateTime}.
     */
    public ParsedDateTime startDate() {
        return from;
    }


    /**
     * Returns the end date of the event.
     *
     * @return The end date of the event as a {@link ParsedDateTime}.
     */
    public ParsedDateTime endDate() {
        return to;
    }

    /**
     * Returns the string representation of the Event task for display.
     * @return formatted string
     *         e.g. "[E][X] project meeting (from: Jan 22 2026 14:00
     *         to: Jan 22 2026 16:00)"
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + from + " to: " + to + ")";
    }

    /**
     * Returns the string representation used for file storage for Event task.
     * @return formatted string
     *         e.g. "E | 1 | project meeting | 2026-01-22 14:00 | 2026-01-22 16:00"
     */
    @Override
    public String toStorageString() {
        return "E " + super.toStorageString() + " | " + from.toStorageString()
                + " | " + to.toStorageString();
    }

    /**
     * Parses a command string to create a new {@link Event} task.
     * <p>
     * Command to follow the following format:
     *      event <name of task> /from <start time> /to <end time>
     * <p>
     * All 3 fields (name of task, start and end time), must be provided,
     * otherwise {@link InvalidFormatException} is shown and the task is not being added into the
     * list.
     *
     * @param cmd The full command entered by the user
     *            e.g. "event project meeting /from 2026-01-22 12:00
     *            /to 2026-01-22 18:00".
     * @return A new {@link Event} task object.
     * @throws InvalidFormatException If task name, start time, or end time
     *                                is missing.
     */
    public static Event parse(String cmd)
            throws InvalidFormatException {
        String[] parts = cmd.substring(5)
                .split(" /from | /to ", 3);
        if (parts.length < 3
                || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty()
                || parts[2].trim().isEmpty()) {
            throw new InvalidFormatException("Oops! Event requires /from," +
                    " /to and name");
        }
        return new Event(parts[0].trim(),
                ParsedDateTime.dateTimeParser(parts[1].trim()),
                ParsedDateTime.dateTimeParser(parts[2].trim()));
    }
}
