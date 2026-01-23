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

    /**
     * Creates and adds a Todo task to the task list
     * <p>
     * Command to follow the following format:
     *      todo <name of task>
     * <p>
     * If name of task is missing or empty, an error message is shown and task is
     * not being added into the list
     *
     * @param cmd Full command entered by the user e.g. "todo read book"
     * @return A ToDo task object
     * @throws InvalidFormatException if task name is missing
     */
    public static ToDo parse(String cmd)
            throws InvalidFormatException {
        String taskName = cmd.substring(4).trim();

        if (taskName.isEmpty()) {
            throw new InvalidFormatException("Oops! The task name is missing ");
        }

        return new ToDo(taskName);
    }
}
