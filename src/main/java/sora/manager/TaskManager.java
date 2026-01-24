package sora.manager;

import sora.storage.Storage;
import sora.task.Task;

import java.util.ArrayList;

/**
 * Manages the storage and retrieval of tasks for Sora.
 * <p>
 * Responsible for adding and retrieving tasks,
 * and providing the current task list and task count.
 */
public class TaskManager {

    private final Storage storage = new Storage();
    private final ArrayList<Task> tasks = new ArrayList<>(storage.load());


    /**
     * Retrieve the array of all tasks.
     *
     * @return An {@link ArrayList} of {@link Task} objects.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Retrieve the current number of tasks in the task list.
     *
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Adds a new task to the task list
     * and persists the updated list.
     *
     * @param task The {@link Task} object to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        storage.save(tasks);
    }

    /**
     * Retrieves a task by its index.
     *
     * @param index The index of the task.
     * @return The {@link Task} object if index is valid;
     *         {@code null} otherwise.
     */
    public Task getTask(int index) {
        if (index < 0 || index >= this.getTaskCount()) {
            return null;
        }

        return tasks.get(index);
    }

    /**
     * Removes a task by its index
     * and persists the updated list.
     *
     * @param index The index of the task.
     * @return The {@link Task} object if index is valid;
     *         {@code null} otherwise.
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
     * Marks task of the specified index as completed.
     * <p>
     * If the index is invalid or out-of-bounds, the method will return {@code null}
     * and no changes are made.
     *
     * @param index The index of the task to mark as completed.
     * @return The {@link Task} object if index is valid;
     *         {@code null} otherwise.
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
     * Marks task of the specified index as not completed.
     * <p>
     * If the index is invalid or out-of-bounds, the method will return {@code null}
     * and no changes are made.
     *
     * @param index The index of the task to mark as completed.
     * @return The {@link Task} object if index is valid;
     *         {@code null} otherwise.
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
