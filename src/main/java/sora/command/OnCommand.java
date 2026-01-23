package sora.command;

import sora.exception.InvalidFormatException;
import sora.manager.TaskManager;
import sora.task.Deadline;
import sora.exception.SoraException;
import sora.parser.ParsedDateTime;
import sora.task.Event;
import sora.task.Task;
import sora.ui.Ui;

import java.util.ArrayList;

/**
 * Command to list all tasks occurring on the specified date
 */
public class OnCommand implements Command {

    private final ParsedDateTime targetDate;

    /**
     * Constructs an OnCommand for the given date
     * <p>
     * Note: Time will be ignored when matching task
     *
     * @param targetDate The date string input by the user. Accepts either
     *                   "yyyy-MM-dd" or "yyyy-MM-dd HH:mm".
     * @throws InvalidFormatException if date string input is not valid
     */
    public OnCommand(String targetDate) {
        this.targetDate = ParsedDateTime.dateTimeParser(targetDate);
    }

    /**
     * Executes the command by iterating through the tasks and find the
     * tasks that occur on the target date.
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     * @throws SoraException for any internal errors during the execution
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        ArrayList<Task> tasks = taskManager.getTasks();
        ArrayList<Task> matched = new ArrayList<>();

        for (Task task: tasks) {
            if (task instanceof Deadline deadline) {
                if (deadline.deadline().getDate().equals(targetDate.getDate())) {
                    matched.add(task);
                }
            } else if (task instanceof Event event) {
                if (event.startDate().getDate().equals(targetDate.getDate())
                        || event.endDate().getDate().equals(targetDate.getDate())) {
                    matched.add(task);
                }
            }
        }

        if (matched.isEmpty()) {
            Ui.showError("Hmm... No tasks found on " + targetDate.toString() + "." +
                    "\n Looks like a free day! ");
        } else {
            ui.showTasks(matched, matched.size());
        }
    }
}
