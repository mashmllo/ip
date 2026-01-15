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
     * @return formatted string e.g. "[X] read book"
     */
    @Override
    public String toString() {
        String statusIcon = "[" + getStatusIcon() + "]";
        return String.format("%s %s", statusIcon, name);
    }
}
