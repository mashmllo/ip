package sora.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sora.exception.InvalidFormatException;
import sora.task.Deadline;
import sora.task.Task;
import sora.task.ToDo;
import sora.ui.OutputHandler;

public class StorageTest {

    @TempDir
    Path tempDir;

    private Path testFile;
    private FakeOutputHandler fakeOutput;
    private Storage storage;

    private static class FakeOutputHandler implements OutputHandler {
        private String lastMessage;

        @Override
        public void show(String message) throws InvalidFormatException {
            this.lastMessage = message;
        }

        @Override
        public void showError(String message) throws InvalidFormatException {
            this.lastMessage = message;
        }
    }

    @BeforeEach
    void setUp() {
        this.fakeOutput = new FakeOutputHandler();

        this.storage = new Storage(fakeOutput) {
            @Override
            public ArrayList<Task> load() {
                return super.load();
            }
        };

        this.testFile = this.tempDir.resolve("sora.txt");

        try {
            Field field = Storage.class.getDeclaredField("path");
            field.setAccessible(true);
            field.set(this.storage, this.testFile);
        } catch (NoSuchFieldException noSuchFieldException) {
            fail("Failed to inject test file path");
        } catch (IllegalAccessException illegalAccessException) {
            fail("Illegal Access to the storage and test file");
        }
    }


    @Test
    public void load_fileDoesNotExist_returnsEmptyList() {
        ArrayList<Task> tasks = storage.load();
        assertTrue(tasks.isEmpty());
        assertNotNull(fakeOutput.lastMessage);
    }

    @Test
    public void load_validTasks_success() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "T | 0 | read book",
                "D | 1 |  submit CS2103 report | 2026-02-16 11:00",
                "E | 0 |  CS2103 Project Meeting | 2026-02-16 09:00 | 2026-02-16 12:00"
        ));

        ArrayList<Task> tasks = storage.load();

        assertEquals(3, tasks.size());
    }

    @Test
    public void load_corruptedLine_skipsLine() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "T | 0 | read book",
                "INVALID LINE"
        ));

        ArrayList<Task> tasks = storage.load();

        assertEquals(1, tasks.size());
        assertNotNull(fakeOutput.lastMessage);
    }

    @Test
    public void save_nullTaskList_throwException() {
        assertThrows(IllegalArgumentException.class, ()
                -> storage.save(null));
    }

    @Test
    public void save_validTasks_fileCreated() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));

        storage.save(tasks);

        assertTrue(Files.exists(this.testFile));
        assertFalse(Files.readAllLines(this.testFile).isEmpty());
    }

    @Test
    public void saveThenLoad_tasksRemainSame() {
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(new ToDo("read book"));
        Deadline deadline = new Deadline(
                "submit report",
                sora.parser.ParsedDateTime.dateTimeParser("2026-02-16 11:00")
                );
        deadline.markAsDone();
        tasks.add(deadline);
        storage.save(tasks);

        ArrayList<Task> loadTasks = storage.load();

        assertEquals(tasks.size(), loadTasks.size());
        assertEquals(tasks.get(0).toString(), tasks.get(0).toString());
    }

    @Test
    public void save_directoryDoesNotExist_directoryCreated() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));

        storage.save(tasks);

        assertTrue(Files.exists(this.testFile.getParent()));
    }

    @Test
    public void load_invalidCompletionStatus_skipsTask() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "T | 9 | read book",
                "T | -1 | swim"
        ));

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_deadlineMissingDate_skipsTask() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "D | 1 |  submit CS2103 report | ",
                "D | 1 |  submit CS2103 project | "
        ));

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_eventMissingStart_skipsTask() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "E | 0 |  CS2103 Project Meeting |  | 2026-02-16 12:00",
                "E | 0 |  Career Fair |  | 2026-02-16 18:00"
        ));

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_eventMissingEnd_skipsTask() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "E | 0 |  CS2103 Project Meeting | 2026-02-16 09:00 | ",
                "E | 0 |  Career Fair | 2026-02-16 12:00 | "
        ));

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_unknownTaskType_skipsTask() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "X | 0 | read book",
                "Y | 0 | swim"
        ));

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_multipleCorruptedLines_validTasksStillLoads() throws IOException {
        Files.write(this.testFile, Arrays.asList(
                "INVALID",
                "T | 0 | read book",
                "X | 0 | unknown",
                "T | 1 | swim"
        ));

        ArrayList<Task> tasks = storage.load();

        assertEquals(2, tasks.size());
    }

    @Test
    public void save_io_showsErrorMessage() throws IOException {

        Files.createDirectories(testFile);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read book"));

        storage.save(tasks);
        assertNotNull(this.fakeOutput.lastMessage);
    }

    @Test
    public void load_largeNumberOfTasks_success() throws IOException {

        ArrayList<String> lines = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            lines.add("T | 0 | task " + i);
        }

        Files.write(this.testFile, lines);

        ArrayList<Task> tasks = storage.load();
        assertEquals(500, tasks.size());
    }

}
