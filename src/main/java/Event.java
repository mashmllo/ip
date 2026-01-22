/**
 * Event task for Sora
 * <p>
 *  A Event task are tasks that occurs during a specific time period.
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
}
