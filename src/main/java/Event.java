/**
 * Event task for Sora
 * <p>
 *  A Event task are tasks that occurs during a specific time period.
 * This class extends the base Task class and adds a "from" and "to" field to
 * store the event duration.
 * The marks task type as [E].
 */
public class Event extends Task {

    private String from;
    private String to;

    /**
     * Constructor of a new Event class
     * @param name  Name of the event
     * @param from  Start date/time of the event
     * @param to    end date/time of the event
     */
    public Event(String name, String from, String to) {
        super(name);
        this.from = from;
        this.to = to;
    }

    /**
     * Return the string representation of the Event task.
     * @return formatted string e.g. "[E][X] project meeting (from: Mon 2pm to: 4pm)"
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + from + " to: " + to + ")";
    }

    /**
     * Return string representation used for file storage for Event task.
     * @return formatted string e.g. "E | 1 | project meeting | June 6th 2pm | 4pm"
     */
    @Override
    public String toStorageString() {
        return "E " + super.toStorageString() + " | " + from + " | " + to;
    }
}
