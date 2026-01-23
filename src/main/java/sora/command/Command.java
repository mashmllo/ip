package sora.command;

import sora.exception.SoraException;
import sora.manager.TaskManager;
import sora.ui.Ui;

/**
 * Represents an executable user command used in Sora
 */
public interface Command {

    /**
     * Executes the command
     *
     * @param ui The user interface class
     * @param taskManager  The task manager class
     * @throws SoraException if execution fails due to user input
     */
    void execute(TaskManager taskManager, Ui ui) throws SoraException;
}
