package cash;

import cash.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper {

    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH+"common");
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        try {
            String text = bis.readLine();
            if(text.equalsIgnoreCase(res.getString("operation.EXIT"))){
                throw new InterruptOperationException();
            }
            return text;
        } catch (IOException ignored) { //suppose it will never occur
        }
        return null;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        while(true) {
            System.out.println(res.getString("choose.currency.code"));
            String code = readString();
            if (code.length() == 3) {
                return code.trim().toUpperCase(Locale.ROOT);
            } else {
                writeMessage("Error");
            }
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        while(true) {
            writeMessage(String.format( res.getString("choose.denomination.and.count.format"), currencyCode));
            String s = readString();
            String[] split = null;
            if(s == null || (split = s.split(" ")).length != 2){
                writeMessage("Error");
                continue;
            } else{
                try{
                    if(Integer.parseInt(split[0])<=0 || Integer.parseInt(split[1])<=0){
                        writeMessage("Error");
                    }
                }catch (NumberFormatException e){
                    writeMessage("Error");
                    continue;
                }
            }
            return split;
        }
    }

    public static Operation askOperation() throws InterruptOperationException {
        while (true) {
            writeMessage(String.format(res.getString("choose.operation")+":%n1 - "+res.getString("operation.INFO")+"%n2 - "+res.getString("operation.DEPOSIT")+"%n3 - "+res.getString("operation.WITHDRAW")+"%n4 - "+res.getString("operation.EXIT")));
            int i = Integer.parseInt(readString());
            if(i == 0){
                throw new IllegalArgumentException();
            }
            try {
                return Operation.getAllowableOperationByOrdinal(i);
            } catch (IllegalArgumentException e) {
                writeMessage("Error");
            }
        }
    }

    public static void printExitMessage(){
        ConsoleHelper.writeMessage(res.getString("the.end"));
    }
}
