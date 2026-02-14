package sora.task;

/**
 * Represents a general task in Sora.
 * <p>
 * Each task has a {@code name} and a completion status
 * indicating whether it is done.
 * This class serves as the base class for all specific
 * task types, such as {@link ToDo}, {@link Deadline} and
 * {@link Event}.
 */
public abstract class Task {

    private static final char DONE = '1';
    private static final char NOT_DONE = '0';

    private final String name;
    private boolean isDone;

    /**
     * Constructs a new task with the given name.
     *
     * @param name The name of the task.
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Marks task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Checks the status of the task and return the relevant symbol.
     *
     * @return "X" if done, otherwise " ".
     */
    private String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /**
     * Returns the task as a formatted string with its status.
     *
     * @return formatted string e.g. "[X] read book".
     */
    @Override
    public String toString() {
        String statusIcon = "[" + getStatusIcon() + "]";
        return String.format("%s %s", statusIcon, this.name);
    }

    /**
     * Returns the task as a formatted string with its status
     * to be stored in a file.
     * <p>
     * The completion status is encoded as follows:
     * <ul>
     *     <li> {@code 1 } - task is completed </li>
     *     <li> {@code 0 } - task is not completed </li>
     * </ul>
     *
     * @return The formatted string e.g. "| 1 | read book".
     */
    public String toStorageString() {
        String statusIcon = "| " + (this.isDone ? DONE : NOT_DONE) + " | ";
        return String.format("%s %s", statusIcon, this.name);
    }
}
