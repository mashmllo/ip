package sora.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.parser.ParsedDateTime;
import sora.task.Deadline;
import sora.task.Event;
import sora.task.Task;
import sora.task.ToDo;
import sora.ui.Ui;

/**
 * Handles storing and loading of tasks to and from persistent storage.
 * <p>
 * The data file and its parent directories will be
 * created automatically if they do not exist.
 */
public class Storage {

    private final Path path;

    /**
     * Constructs a new {@code Storage} instance with default storage file path set to
     * {@code data/sora.txt}.
     * <p>
     * The file and directories will be created
     * automatically if they do not exist
     * when {@link #save(ArrayList)} is called.
     */
    public Storage() {
        this.path = Paths.get("data", "sora.txt");
    }

    /**
     * Loads tasks from disk
     * <p>
     * Behavior:
     * <ul>
     *     <li>If the memory file does not exist, an empty list is returned.</li>
     *     <li>If a line in the file is corrupted, it is skipped and an error.
     *         message is shown </li>
     *     <li>If an I/O error occurs while reading the file, an error is show.n</li>
     * </ul>
     *
     * @return ArrayList containing all tasks successfully loaded tasks.
     */
    public ArrayList<Task> load() {

        ArrayList<Task> tasks = new ArrayList<>();

        //Check if path is initialized and file exist
        if (!Files.exists(this.path)) {
            Ui.showError("Hmm... memory file not found"
                    + "\n Starting with an empty task list...");
            return tasks;
        }

        try (BufferedReader reader = Files.newBufferedReader(this.path)) {
            readTask(reader, tasks);
        } catch (IOException ioException) {
            Ui.showError("Oops! I couldn't read my memory file"
                    + "\n You can still use Sora, but past tasks won't be"
                    + " loaded");
            return tasks;
        }

        return tasks;
    }

    /**
     * Saves the given list of tasks to the storage file on the disk.
     * <p>
     * The parent directory is created automatically if file does not exist.
     * I/O errors encountered will be handled and users will be informed of
     * the error.
     *
     * @param tasks List of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        ensureDirectoryExist();

        try (BufferedWriter writer = Files.newBufferedWriter(this.path)) {
            for (Task task : tasks) {
                writer.write(saveTask(task));
                writer.newLine();
            }
        } catch (IOException ioException) {
            Ui.showError("Oops! Failed to save tasks");
        }
    }

    /**
     * Ensures that the parent directory of the storage file exists.
     * If the directory does not exist, it will be created automatically.
     * <p>
     * An error will also be displayed to the user if the creation of the
     * directory fails.
     *
     */
    private void ensureDirectoryExist() {
        if (this.path.getParent() != null) {
            try {
                Files.createDirectories(this.path.getParent());
            } catch (IOException ioException) {
                Ui.showError("Oops! Failed to create directories"
                        + "\n Please check your permissions and try again");
            }
        }
    }

    /**
     * Reads task from the given BufferedReader and adds them to the ArrayList.
     * <p>
     * Each line in the reader will be parsed individually. If the task cannot be
     * parsed or is corrupted, it will be skipped and an error message will also be
     * displayed to the user.
     *
     * @param reader    The BufferedReader from which to read task lines.
     * @param tasks     The ArrayList where successfully parsed tasks will be added to.
     * @throws IOException  If an I/O exception occur while reading from the reader.
     */
    private void readTask(BufferedReader reader, ArrayList<Task> tasks)
            throws IOException {

        String line;
        int lineNo = 0;

        while ((line = reader.readLine()) != null) {
            lineNo++;
            try {
                tasks.add(loadTask(line));
            } catch (SoraException soraException) {
                Ui.showError("Hmm... I skipped a corrupted task at "
                        + lineNo
                        + "\n It won't affect the rest of your list");
            }
        }

    }

    /**
     * Parses a line from storage file to Task object.
     * <p>
     * This method validates the type, name, and any additional fields used by
     * different task type.
     * It also sets the completion status according to the second field.
     *
     * @param line Line from the storage file.
     * @return A {@link Task} object corresponding to the line.
     * @throws InvalidFormatException If the line is malformed or missing required fields,
     *                                contains unknown task types, or invalid completion
     *                                status.
     */
    private Task loadTask(String line) throws InvalidFormatException {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new InvalidFormatException("Hmm... your input seems too short"
                    + "\n Please provide more information");
        }

        String type = parts[0].trim();
        String completedStr = parts[1].trim();
        String name = parts[2].trim();

        Task task;

        if (type.equals("T")) {
            task = new ToDo(name);
        } else if (type.equals("D")) {
            if (parts.length < 4 || parts[3].trim().isEmpty()) {
                throw new InvalidFormatException("Oops! Deadline requires /by "
                        + "and name");
            }
            task = new Deadline(name, ParsedDateTime.dateTimeParser(parts[3].trim()));
        } else if (type.equals("E")) {
            if (parts.length < 5
                    || parts[3].trim().isEmpty()
                    || parts[4].trim().isEmpty()) {
                throw new InvalidFormatException("Oops! Event requires /from,"
                        + " /to and name");
            }
            task = new Event(name,
                    ParsedDateTime.dateTimeParser(parts[3].trim()),
                    ParsedDateTime.dateTimeParser(parts[4].trim()));
        } else {
            throw new InvalidFormatException("Oops! Unknown task type");
        }

        setTaskCompletionStatus(task, completedStr);
        return task;

    }

    /**
     * Sets completion status of a task based on the string in storage.
     * <p>
     * The completedStat should be:
     * <ul>
     *     <li>"0" - denotes the task as not done </li>
     *     <li>"1" - denotes the task as done </li>
     * </ul>
     * @param task          Task whose completion status should be set.
     * @param completedStat  String representation of the completion status.
     * @throws InvalidFormatException if completedStr is not "0" or "1".
     */
    private void setTaskCompletionStatus(Task task, String completedStat)
            throws InvalidFormatException {

        if (completedStat.equals("0")) {
            task.markAsNotDone();
        } else if (completedStat.equals("1")) {
            task.markAsDone();
        } else {
            throw new InvalidFormatException("Oops! Unknown completion status");
        }

    }

    /**
     * Converts a task into its storage string representation.
     *
     * @param task Task to be converted.
     * @return String format of the task.
     * @throws IllegalStateException  If unsupported task type is entered.
     */
    private String saveTask(Task task)
            throws IllegalStateException {

        if (task instanceof ToDo toDo) {
            return toDo.toStorageString();
        }

        if (task instanceof Deadline deadline) {
            return deadline.toStorageString();
        }

        if (task instanceof Event event) {
            return event.toStorageString();
        }

        throw new IllegalStateException("Oops! Unknown task type");
    }

}
