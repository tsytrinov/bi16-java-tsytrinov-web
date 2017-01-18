package Banking;

import java.math.BigDecimal;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class Transaction {

    private int transactionID;
    private BigDecimal amount;
    private int fromCreditCardID;
    private int toCreditCardID;

    public Transaction(int transactionID, BigDecimal amount, int fromCreditCardID, int toCreditCardID){
        this.transactionID = transactionID;
        this.amount = amount;
        this.fromCreditCardID = fromCreditCardID;
        this.toCreditCardID = toCreditCardID;
    }


    public int getTransactionID(){
        return transactionID;
    }
    public BigDecimal getAmount(){
        return amount;
    }
    public int getFromCreditCardID(){
        return fromCreditCardID;
    }
    public int getToCreditCardID(){
        return toCreditCardID;
    }


}
