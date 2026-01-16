/**
 * Manages the storage and retrieval of tasks for Sora
 * <p>
 * This class is responsible for adding new tasks, retrieving tasks,
 * and providing the current task list and count.
 */
public class TaskManager {

    private final Task[] tasks = new Task[100];
    private int taskCount = 0;

    /**
     * Adds a new task to array
     *
     * @param task Task object to add
     */
    public void addTask(Task task) {
        tasks[taskCount] = task;
        taskCount++;
    }

    /**
     * Retrieve task by index
     *
     * @param index Index of the task
     * @return Task object if index is valid, otherwise null
     */
    public Task getTask(int index) {
        if (index < 0 || index >= taskCount) {
            return null;
        }

        return tasks[index];
    }
    /**
     * Retrieve the array of all tasks
     *
     * @return Array of Task objects
     */
    public Task[] getTasks() {
        return tasks;
    }

    /**
     * Retrieve the current number of tasks
     *
     * @return  Number of tasks
     */
    public int getTaskCount() {
        return taskCount;
    }


}
