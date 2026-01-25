package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.ui.Ui;

/**
 * Command that exits the application
 */
public class ExitCommand implements Command {

    /**
     * Executes the exit command
     * <p>
     * Note: no action is required here as application terminates
     *
     * @param taskManager  The task manager class
     * @param ui The user interface class
     * @throws SoraException If command execution fails.
     */
    @Override
    public void execute(TaskManager taskManager, Ui ui) throws SoraException {
    }

}
