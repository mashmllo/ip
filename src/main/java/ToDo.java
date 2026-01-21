/**
 * ToDo task for Sora
 *  <p>
 *  A ToDo task are tasks without any date/time attached to it.
 *  This class extends the base Task class and marks task type as [T]
 */
public class ToDo extends Task {

    /**
     * Constructor of the new ToDo task
     * @param name Name of the ToDo task
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Return the string representation of the ToDo task.
     * @return formatted string e.g. "[T][X] read book"
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Return string representation used for file storage for ToDo task.
     * @return formatted string e.g. "T | 1 | read book"
     */
    @Override
    public String toStorageString() {
        return "T " + super.toStorageString();
    }
}
