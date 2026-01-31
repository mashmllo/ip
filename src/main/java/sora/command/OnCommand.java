package sora.command;

import java.util.ArrayList;

import sora.exception.InvalidFormatException;
import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.parser.ParsedDateTime;
import sora.task.Deadline;
import sora.task.Event;
import sora.task.Task;
import sora.ui.Ui;

/**
 * Represents a command to list all tasks occurring on the specified date.
 */
public class OnCommand implements Command {

    private final ParsedDateTime targetDate;

    /**
     * Constructs an {@code OnCommand} for the given date.
     * <p>
     * Note: Time will be ignored when matching task
     *
     * @param targetDate The date string input by the user. Accepts either
     *                   {@code yyyy-MM-dd} or {@code yyyy-MM-dd HH:mm} format.
     */
    public OnCommand(String targetDate) throws InvalidFormatException {
        this.targetDate = ParsedDateTime.dateTimeParser(targetDate);
    }

    /**
     * Executes the command by iterating through the tasks and
     * identifying tasks that occur on the target date.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException If an error occurs during command execution.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Task> matchedTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof Deadline deadline) {
                if (deadline.getDeadline().getDate().equals(this.targetDate.getDate())) {
                    matchedTasks.add(task);
                }
            } else if (task instanceof Event event) {
                if (event.getStartDate().getDate().equals(this.targetDate.getDate())
                        || event.getEndDate().getDate().equals(this.targetDate.getDate())) {
                    matchedTasks.add(task);
                }
            }
        }

        if (matchedTasks.isEmpty()) {
            Ui.showError("Hmm... No tasks found on " + this.targetDate.toString()
                    + "."
                    + "\n Looks like a free day! ");
        } else {
            ui.showTasks(matchedTasks, matchedTasks.size());
        }
    }
}
