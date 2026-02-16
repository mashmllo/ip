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

    private static final String NO_TASK_MSG_TEMPLATE =
            "Hmm... No tasks found on %s."
                    + "\n Looks like a free day! ";

    private final ParsedDateTime targetDate;

    /**
     * Constructs an {@code OnCommand} for the given date.
     * <p>
     * Note: Time will be ignored when matching task.
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
     * @throws NullPointerException If either {@code taskManager} or {@code ui} is
     *                              {@code null}. This exception is thrown to indicate
     *                              improper initialization of
     *                              the object.
     * @throws SoraException If an error occurs during command execution.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
        if (taskManager == null) {
            throw new NullPointerException("TaskManager object should not be null");
        }

        if (ui == null) {
            throw new NullPointerException("Ui object should not be null");
        }

        ArrayList<Task> matchedTasks = getMatchedTask(taskManager.getTasks());
        displayMatchedTask(ui, matchedTasks);
    }


    /**
     * Filters the list of tasks and returns those that occurs on the target date.
     *
     * @param tasks List of tasks to search.
     * @return List of tasks matching the target date.
     * @throws NullPointerException If {@code task} is null.
     */
    private ArrayList<Task> getMatchedTask(ArrayList<Task> tasks)
            throws NullPointerException {
        ArrayList<Task> matchedTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task == null) {
                throw new NullPointerException("Task in list should not be null");
            }

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
        return matchedTasks;
    }

    private void displayMatchedTask(Ui ui, ArrayList<Task> matchedTasks) {
        if (matchedTasks.isEmpty()) {
            ui.showError(String.format(NO_TASK_MSG_TEMPLATE, this.targetDate));
        }

        ui.showTasks(matchedTasks, matchedTasks.size());
    }
}
