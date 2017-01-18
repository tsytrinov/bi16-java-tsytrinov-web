package Banking;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class MySQLDAOFactory extends DAOFactory {

    public TransactionDAOImpl getTransactionDAO() {
        return new TransactionDAOImpl();
    }
    public UserDAOImpl getUserDAO() {
        return new UserDAOImpl();
    }
    public CreditCardDAOImpl getCreditCardDAO() {
        return new CreditCardDAOImpl();
    }
}
