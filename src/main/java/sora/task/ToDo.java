package sora.task;

import sora.exception.InvalidFormatException;

/**
 * Represents a ToDo task in Sora.
 *  <p>
 *  A ToDo task is a tasks without any date or time attached.
 *  This class extends {@link Task} class and marks task type as [T].
 */
public class ToDo extends Task {

    private static final int TODO_CMD_LENGTH = 4;

    /**
     * Constructs of the new ToDo task with the given name.
     * @param name The name of the ToDo task.
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Returns the string representation of the ToDo task for display.
     * @return The formatted string e.g. "[T][X] read book".
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns string representation used for file storage for ToDo task.
     * @return The formatted string e.g. "T | 1 | read book".
     */
    @Override
    public String toStorageString() {
        return "T " + super.toStorageString();
    }

    /**
     * Parses a command string into a Todo task.
     * <p>
     * Command to follow the following format:
     *      todo &lt;name of task&gt;
     * <p>
     * If name of task is missing or empty, {@link InvalidFormatException} is shown and task is
     * not being added into the list
     *
     * @param cmd The full command entered by the user e.g. "todo read book".
     * @return A {@link ToDo} task object.
     * @throws InvalidFormatException If task name is missing or input command is empty.
     */
    public static ToDo parse(String cmd)
            throws InvalidFormatException {
        if (cmd == null || cmd.isBlank()) {
            throw new InvalidFormatException("Command string must not be null or empty");
        }

        String taskName = cmd.substring(TODO_CMD_LENGTH).trim();

        if (taskName.isEmpty()) {
            throw new InvalidFormatException("Oops! The task name is missing");
        }

        return new ToDo(taskName);
    }
}
