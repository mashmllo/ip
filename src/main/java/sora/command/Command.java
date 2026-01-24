package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.ui.Ui;

/**
 * Represents an executable user command used in the Sora application.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param taskManager Manager class used to manage the list of tasks.
     * @param ui          User interface class used to display messages.
     * @throws SoraException if execution fails due to invalid user input.
     */
    void execute(TaskManager taskManager, Ui ui) throws SoraException;
}
