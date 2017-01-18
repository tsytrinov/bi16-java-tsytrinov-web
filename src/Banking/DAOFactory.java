package Banking;

/**
 * Created by tsytrin on 08.01.2017.
 */
public abstract class DAOFactory {

    public abstract TransactionDAOImpl getTransactionDAO();
    public abstract UserDAOImpl getUserDAO();
    public abstract CreditCardDAOImpl getCreditCardDAO();

    // We can define DAO providers for different DB backends
    public static final int MYSQL = 1;

    public static DAOFactory getDAOFactory(
            int whichFactory) {

        switch (whichFactory) {
            case MYSQL:
                return new MySQLDAOFactory();
            default           :
                return null;
        }
    }
}
