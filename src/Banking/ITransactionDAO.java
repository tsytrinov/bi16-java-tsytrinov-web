package Banking;

import java.util.List;

/**
 * Created by tsytrin on 08.01.2017.
 */
public interface ITransactionDAO {
    public int delete(int transactionID);
    public List<Transaction> getAll();
    public Transaction get(int transactionID);
    public int insert(Transaction transaction);
    public int update(Transaction transaction);
    public List<Transaction> getOutGoingTransactionsForUser(int userID);
    public List<Transaction> getIncomingTransactionsForUser(int userID);
}
