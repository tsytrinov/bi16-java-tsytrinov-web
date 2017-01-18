package Banking;

import java.math.BigDecimal;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class CreditCard {
    private int creditCardID;
    private BigDecimal cashAmount;
    private int userID;

    public CreditCard(int creditCardID, BigDecimal cashAmount, int userID){
        this.userID = userID;
        this.creditCardID = creditCardID;
        this.cashAmount = cashAmount;
    }

    public int getCreditCardID(){return creditCardID;}
    public BigDecimal getCashAmount(){return cashAmount;}
    public int getUserID(){return userID;}

}
