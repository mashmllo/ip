/**
 * A task object to encapsulate task data
 * <p>
 * Each task has a name and a status to indicate whether it is done.
 */
public class Task {

    private final String name;
    private boolean isDone;

    /**
     * Constructor of the new task
     * @param name name of the task
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Mark task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Mark task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Check the status of the task and return the relevant symbol
     *
     * @return "X" if done, otherwise " "
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Return the task as a formatted string with its status
     *
     * @return formatted string e.g. "[X] read book"
     */
    @Override
    public String toString() {
        String statusIcon = "[" + getStatusIcon() + "]";
        return String.format("%s %s", statusIcon, name);
    }

    /**
     * Returns the task as a formatted string with its status
     * to be stored in a file
     * <p>
     * The completion status is encoded as follows:
     * <ul>
     *     <li> {@code 1 } - task is completed </li>
     *     <li> {@code 0 } - task is not completed </li>
     * </ul>
     *
     * @return formatted string e.g. "| 1 | read book"
     */
    public String toStorageString() {
        String statusIcon = "| " + (isDone ? '1' : '0') + " | ";
        return String.format("%s %s", statusIcon, name);
    }
}
