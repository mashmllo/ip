package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.ui.Ui;

/**
 * Represents a command that exits the application.
 */
public class ExitCommand implements Command {

    /**
     * Executes the exit command.
     * <p>
     * Note: no action is required here as application will terminate
     * after execution.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException No exceptions are thrown in this implementation.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
    }

}
