package sora.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import sora.exception.InvalidFormatException;

public class TaskTest {

    @Test
    public void newTask_defaultState_notDone() {
        Task task = new ToDo("read book");
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parse_deadlineDateOnly_success() {
        Task task = Deadline.parse("deadline homework /by 2026-01-25");
        assertEquals("[D][ ] homework (by: Jan 25 2026)", task.toString());
    }


    @Test
    public void parse_deadlineDateTime_success() {
        Task task = Deadline.parse("deadline homework /by 2026-01-25 12:00");
        assertEquals("[D][ ] homework (by: Jan 25 2026 12:00)",
                task.toString());
    }

    @Test
    public void parse_deadlineMissingBy_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework"));
    }

    @Test
    public void parse_deadlineInvalidDay_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework /by 2026-02-30"));
    }

    @Test
    public void parse_deadlineInvalidMonth_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework /by 2026-13-01"));
    }

    @Test
    public void parse_deadlineLeapYear_valid() throws InvalidFormatException {
        Task task = Deadline.parse("deadline homework /by 2024-02-29");
        assertEquals("[D][ ] homework (by: Feb 29 2024)", task.toString());
    }

    @Test
    public void parse_deadlineLeapYear_invalid() throws InvalidFormatException {
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework /by 2023-02-29"));
    }

    @Test
    public void parse_deadlineInvalidTime_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework /by 2026-02-01 24:00"));
        assertThrows(InvalidFormatException.class, ()
                -> Deadline.parse("deadline homework /by 2026-02-01 12:60"));
    }

    @Test
    public void parse_eventDateOnly_success() {
        Task task = Event.parse("event career fair "
                + "/from 2026-01-25 /to 2026-01-27");
        assertEquals("[E][ ] career fair (from: Jan 25 2026 to: Jan 27 2026)",
                task.toString());
    }


    @Test
    public void parse_eventDateTime_success() {
        Task task = Event.parse("event career fair "
                + "/from 2026-01-25 12:00 /to 2026-01-25 18:00");
        assertEquals("[E][ ] career fair "
                        + "(from: Jan 25 2026 12:00 to: Jan 25 2026 18:00)",
                task.toString());
    }

    @Test
    public void parse_eventMissingDuration_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Event.parse("event career fair"));
    }

    @Test
    public void parse_eventInvalidDateFormat_throwsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> Event.parse("event career fair "
                        + "/from 26/01/2026 /to 27/01/2026"));
    }

    @Test
    public void markAsDone_once_taskCompleted() {
        Task task = new ToDo("read book");
        task.markAsDone();
        assertEquals("[T][X] read book", task.toString());
    }

    @Test
    public void markAsNotDone_afterDone_taskUnmarked() {
        Task task = new ToDo("read book");
        task.markAsDone();
        task.markAsNotDone();
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void toStorageString_notDone_correctFormat() {
        Task task = new ToDo("read book");
        assertEquals("T | 0 |  read book",
                task.toStorageString());
    }

    @Test
    public void toStorageString_done_correctFormat() {
        Task task = new ToDo("read book");
        task.markAsDone();
        assertEquals("T | 1 |  read book",
                task.toStorageString());
    }

    @Test
    public void markAsDone_twice_idempotent() {
        Task task = new ToDo("read book");
        task.markAsDone();
        task.markAsDone();
        assertEquals("[T][X] read book", task.toString());
    }

    @Test
    public void markAsNotDone_whenNotDone_idempotent() {
        Task task = new ToDo("read book");
        task.markAsNotDone();
        assertEquals("[T][ ] read book", task.toString());
    }
}
