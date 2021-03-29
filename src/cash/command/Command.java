package cash.command;

import cash.exception.InterruptOperationException;

interface Command {
     void execute() throws InterruptOperationException;
}
