package sora.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import sora.command.Command;
import sora.command.ExitCommand;
import sora.command.ListCommand;
import sora.command.OnCommand;
import sora.command.index.DeleteCommand;
import sora.command.index.MarkCommand;
import sora.command.index.UnmarkedCommand;
import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.exception.UnknownCommandException;
import sora.manager.TaskManager;
import sora.task.Deadline;
import sora.task.Event;
import sora.task.Task;
import sora.task.ToDo;
import sora.ui.Ui;


public class CommandParserTest {

    @Test
    public void parse_exitCommand_success() {
        Command cmd = CommandParser.parse("bye");
        assertTrue(cmd instanceof ExitCommand);
    }

    @Test
    public void parse_listCommand_success() {
        Command cmd = CommandParser.parse("list");
        assertTrue(cmd instanceof ListCommand);
    }

    @Test
    public void parse_markCommand_validIndexSuccess() {
        Command cmd = CommandParser.parse("mark 1");
        assertTrue(cmd instanceof MarkCommand);
    }

    @Test
    public void parse_unmarkCommand_validIndexSuccess() {
        Command cmd = CommandParser.parse("unmark 1");
        assertTrue(cmd instanceof UnmarkedCommand);
    }

    @Test
    public void parse_deleteCommand_validIndexSuccess() {
        Command cmd = CommandParser.parse("delete 1");
        assertTrue(cmd instanceof DeleteCommand);
    }

    @Test
    public void parse_onCommand_validTargetSuccess() {
        Command cmd = CommandParser.parse("on 2026-01-24");
        assertTrue(cmd instanceof OnCommand);
    }

    @Test
    public void parseTask_todo_validInputSuccess() {
        Task task = CommandParser.parseTask("todo read book");
        assertTrue(task instanceof ToDo);
        assertEquals("read book", task.toString().substring(7));
    }

    @Test
    public void parseTask_deadline_dateTimeSuccess() {
        Task task = CommandParser
                .parseTask("deadline homework /by 2026-01-26 18:00");
        assertTrue(task instanceof Deadline);
        assertEquals("homework (by: Jan 26 2026 18:00)",
                task.toString().substring(7));
    }

    @Test
    public void parseTask_deadline_dateOnlySuccess() {
        Task task = CommandParser
                .parseTask("deadline Complete CS2103 quiz /by 2026-01-26");
        assertTrue(task instanceof Deadline);
        assertEquals("Complete CS2103 quiz (by: Jan 26 2026)",
                task.toString().substring(7));
    }

    @Test
    public void parseTask_event_dateTimeSuccess() {
        Task task = CommandParser
                .parseTask("event career fair "
                        + "/from 2026-01-25 12:00 /to 2026-01-25 14:00");
        assertTrue(task instanceof Event);
        assertEquals("career fair "
                        + "(from: Jan 25 2026 12:00 to: Jan 25 2026 14:00)",
                task.toString().substring(7));
    }

    @Test
    public void parseTask_event_dateOnlySuccess() {
        Task task = CommandParser
                .parseTask("event career fair "
                        + "/from 2026-01-25 /to 2026-01-26");
        assertTrue(task instanceof Event);
        assertEquals("career fair (from: Jan 25 2026 to: Jan 26 2026)",
                task.toString().substring(7));
    }

    @Test
    public void parseTask_todo_missingNameThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> CommandParser.parse("todo"));
    }


    @Test
    public void parseTask_deadline_missingDateThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> CommandParser.parse("deadline homework"));
    }

    @Test
    public void parseTask_event_missingToThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> CommandParser.parse("event meeting /from 2026-01-25"));
    }

    @Test
    public void parse_emptyInput_throwsSoraExceptions() {
        assertThrows(SoraException.class , () -> CommandParser.parse(""));
    }


    @Test
    public void parse_whitespace_throwsSoraExceptions() {
        assertThrows(SoraException.class , () -> CommandParser.parse(" "));
    }

    @Test
    public void parse_unknownCommand_throwsUnknownCommandException() {
        assertThrows(UnknownCommandException.class , () -> CommandParser.parse("foo"));
    }

    @Test
    public void parse_markCommand_missingIndexThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, () -> CommandParser.parse("mark"));
    }

    @Test
    public void parse_unmarkCommand_nonNumericIndexThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, () -> CommandParser.parse("unmark abc"));
    }

    @Test
    public void parse_deleteCommand_negativeIndexThrowsInvalidFormatException() {
        Command cmd = CommandParser.parse("delete -1");
        TaskManager taskManager = new TaskManager();
        Ui ui = new Ui();

        assertThrows(InvalidFormatException.class, ()
                -> cmd.execute(taskManager, ui));
    }

    @Test
    public void parse_onCommand_missingTargetThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> CommandParser.parse("on "));
    }

    @Test
    public void parse_onCommand_invalidDateFormatThrowsInvalidFormatException() {
        assertThrows(InvalidFormatException.class, ()
                -> CommandParser.parse("on 26/01/2026"));
    }

}
