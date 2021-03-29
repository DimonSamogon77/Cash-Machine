package cash.command;

import cash.CashMachine;
import cash.ConsoleHelper;
import cash.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"exit");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        String conclusion = ConsoleHelper.readString();
        if(conclusion != null && "y".equals(conclusion.toLowerCase())){
            ConsoleHelper.writeMessage(res.getString("thank.message"));
        }else{

        }
    }
}
