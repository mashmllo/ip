import java.util.ArrayList;

/**
 * Manages the storage and retrieval of tasks for Sora
 * <p>
 * This class is responsible for adding new tasks, retrieving tasks,
 * and providing the current task list and count.
 */
public class TaskManager {

    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Retrieve the array of all tasks
     *
     * @return Arraylist of Task objects
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Retrieve the current number of tasks
     *
     * @return  Number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Adds a new task to array
     *
     * @param task Task object to add
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Retrieve task by index
     *
     * @param index Index of the task
     * @return Task object if index is valid, otherwise null
     */
    public Task getTask(int index) {
        if (index < 0 || index >= this.getTaskCount()) {
            return null;
        }

        return tasks.get(index);
    }

    /**
     * Remove task by index
     *
     * @param index Index of the task
     * @return Task object if index is valid, otherwise null
     */
    public Task removeTask(int index) {
        if (index < 0 || index >= this.getTaskCount()) {
            return null;
        }

        return tasks.remove(index);
        Task task = tasks.remove(index);

        return task;
    }
    public Task markTask(int index) {
        if (index < 0 || index >= getTaskCount()) {
            return null;
        }

        Task task = tasks.get(index);
        task.markAsDone();
        return task;
    }

    public Task unmarkTask(int index) {
        if (index < 0 || index >= getTaskCount()) {
            return null;
        }

        Task task = tasks.get(index);
        task.markAsNotDone();
        return task;
    }
}
