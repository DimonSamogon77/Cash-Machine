package cash.command;

import cash.CashMachine;
import cash.ConsoleHelper;
import cash.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command{
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login");


    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        while(true){
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String number = ConsoleHelper.readString();
            String pin = ConsoleHelper.readString();
            if(number == null || number.trim().length() != 12 || pin == null || pin.trim().length()!=4){
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }else try {
                if(validCreditCards.containsKey(number) && pin.equals(validCreditCards.getString(number))){
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), number));
                    break;
                }else{
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), number));
                }
            } catch (NumberFormatException e){
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                continue;
            }
        }
    }
}

