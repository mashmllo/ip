/**
 * Deadline task for Sora
 * <p>
 *  A Deadline task are tasks that needs to be completed by a given date/time.
 * This class extends the base Task class and adds a "by" field to store the deadline.
 * The marks task type as [D].
 */
public class Deadline extends Task {

    private String by;

    /**
     * Constructor of a new Deadline task
     * @param name Name of the task
     * @param by   Due date of the task
     */
    public Deadline(String name, String by) {
        super(name);
        this.by = by;
    }

    /**
     * Return the string representation of the Deadline task.
     * @return formatted string e.g. "[D][X] read book (by: Sunday)"
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
