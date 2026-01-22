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
}
