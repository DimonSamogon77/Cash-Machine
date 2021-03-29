package cash.command;

import cash.CashMachine;
import cash.ConsoleHelper;
import cash.CurrencyManipulator;
import cash.CurrencyManipulatorFactory;
import cash.exception.InterruptOperationException;
import cash.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw");

    @Override
    public void execute() throws InterruptOperationException{
        ConsoleHelper.writeMessage(res.getString("before"));
        try {
            String code = ConsoleHelper.askCurrencyCode();
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
            while (true) {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String s = ConsoleHelper.readString();
                int sum;
                if(s == null){
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                } else{
                    try{
                        sum = Integer.parseInt(s);
                        if(sum<=0){
                            ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                        } else if(currencyManipulator.isAmountAvailable(sum)){
                            Map<Integer, Integer> map = currencyManipulator.withdrawAmount(sum);
                            for(Integer item : map.keySet()){
                                ConsoleHelper.writeMessage("\t" + item+" - "+map.get(item));
                            }

                            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, code));
                            break;
                        }else{
                            ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                            continue;
                        }

                    }catch (NumberFormatException e){
                        ConsoleHelper.writeMessage("specify.not.empty.amount");
                    }
                }
            }
        }catch (NotEnoughMoneyException e) {
            ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
        }
    }
}
