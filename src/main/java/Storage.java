import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles storing and loading of tasks from hard disk
 * <p>
 * Data file will be created automatically if data file or folder does not
 * exist.
 */
public class Storage {

    private final Path path;

    public Storage() {
        this.path = Paths.get("data", "sora.txt");
    }

    /**
     * Saves the given list of tasks to the storage file on the disk
     * <p>
     * The parent directory is created automatically if file does not exist.
     * I/O errors encountered will be handled and users will be informed of the
     * error
     * @param tasks List of tasks to be saved
     */
    public void save(ArrayList<Task> tasks) {
        if (path.getParent() != null) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException ioException) {
                Ui.showError("Oops! Failed to create directories" +
                        "\n Please check your permissions and try again");
            }
        }
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Task task : tasks) {
                writer.write(saveTask(task));
                writer.newLine();
            }
        } catch (IOException ioException) {
            Ui.showError("Oops! Failed to save tasks");
        }
    }

    /**
     * Converts a task into its storage string representation
     *
     * @param task Task to be converted
     * @return String format of the task
     * @throws IllegalStateException  if unsupported task type is entered
     */
    private String saveTask(Task task)
            throws IllegalStateException {

        if (task instanceof ToDo toDo) {
            return toDo.toStorageString();
        } else if (task instanceof Deadline deadline) {
            return deadline.toStorageString();
        } else if (task instanceof Event event) {
            return event.toStorageString();
        }

        throw new IllegalStateException("Oops! Unknown task type");
    }

}
