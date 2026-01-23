package sora.task;

import sora.exception.InvalidFormatException;
import sora.parser.ParsedDateTime;

/**
 * Event task for Sora
 * <p>
 *  An Event task are tasks that occurs during a specific time period.
 * This class extends the base Task class and adds a "from" and "to" field to
 * store the event duration.
 * The marks task type as [E].
 */
public class Event extends Task {

    private final ParsedDateTime from;
    private final ParsedDateTime to;

    /**
     * Constructor of a new Event class
     * @param name  Name of the event
     * @param from  Start date/time of the event
     * @param to    end date/time of the event
     */
    public Event(String name, ParsedDateTime from, ParsedDateTime to) {
        super(name);
        this.from = from;
        this.to = to;
    }


    /**
     * Retrieve the start date of the event
     *
     * @return the start date of the event
     */
    public ParsedDateTime startDate() {
        return from;
    }


    /**
     * Retrieve the end date of the event
     *
     * @return the end date of the event
     */
    public ParsedDateTime endDate() {
        return to;
    }

    /**
     * Return the string representation of the Event task.
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
     * Return string representation used for file storage for Event task.
     * @return formatted string
     *         e.g. "E | 1 | project meeting | 2026-01-22 14:00 | 2026-01-22 16:00"
     */
    @Override
    public String toStorageString() {
        return "E " + super.toStorageString() + " | " + from.toStorageString()
                + " | " + to.toStorageString();
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
     *            e.g. "event project meeting /from 2026-01-22 12:00
     *            /to 2026-01-22 18:00"
     * @return An Event task object
     * @throws InvalidFormatException if task name, start time, or end time
     *                                is missing
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
