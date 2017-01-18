package Banking;

import java.util.List;

/**
 * Created by tsytrin on 08.01.2017.
 */
public interface ICreditCardDAO {
    public int delete(int creditCardID);
    public List<CreditCard> getAll();
    public CreditCard get(int creditCardID);
    public int insert(CreditCard creditCard);
    public int update(CreditCard creditCard);
}
