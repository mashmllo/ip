package sora.manager;

import sora.storage.Storage;
import sora.task.Task;

import java.util.ArrayList;

/**
 * Manages the storage and retrieval of tasks for Sora
 * <p>
 * This class is responsible for adding new tasks, retrieving tasks,
 * and providing the current task list and count.
 */
public class TaskManager {

    private final Storage storage = new Storage();
    private final ArrayList<Task> tasks = new ArrayList<>(storage.load());


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
        storage.save(tasks);
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

        Task task = tasks.remove(index);
        storage.save(tasks);

        return task;
    }

    /**
     * Marks task of the specified index as completed
     * <p>
     * If the index is invalid or out-of-bounds, the method will return null and
     * no changes are made
     *
     * @param index Index of the task to be marked
     * @return The task that was being marked as complete or null if the index
     *         specified is invalid
     */
    public Task markTask(int index) {
        if (index < 0 || index >= getTaskCount()) {
            return null;
        }

        Task task = tasks.get(index);
        task.markAsDone();
        storage.save(tasks);
        return task;
    }

    /**
     * Marks task of the specified index as not complete
     * <p>
     * If the index is invalid or out-of-bounds, the method will return null and
     * no changes are made
     *
     * @param index Index of the task to be marked as not complete
     * @return The task that was being marked as not complete or null if the index
     *         specified is invalid
     */
    public Task unmarkTask(int index) {
        if (index < 0 || index >= getTaskCount()) {
            return null;
        }

        Task task = tasks.get(index);
        task.markAsNotDone();
        storage.save(tasks);
        return task;
    }
}
