package Banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class TransactionDAOImpl implements ITransactionDAO {

    private static final String DELETE = "DELETE FROM Transaction WHERE Transaction_ID=?";
    private static final String FIND_ALL = "SELECT * FROM Transaction ORDER BY Transaction_ID";
    private static final String FIND_BY_ID = "SELECT * FROM Transaction WHERE Transaction_ID=?";
    private static final String INSERT = "INSERT INTO Transaction(Amount, FROM_CreditCard_ID, TO_CreditCard_ID) VALUES(?, ?, ?)";
    private static final String UPDATE = "UPDATE Transaction SET Amount=?, FROM_CreditCard_ID=?, TO_CreditCard_ID=? WHERE Transaction_ID=?";

    private static final String OUTTRANSACTIONS = "SELECT * FROM Transaction WHERE FROM_CreditCard_ID IN " +
            "(SELECT CreditCard_ID FROM CreditCard WHERE Acc_User_ID = ?)";
    private static final String INTRANSACTIONS = "SELECT * FROM Transaction WHERE TO_CreditCard_ID IN " +
            "(SELECT CreditCard_ID FROM CreditCard WHERE Acc_User_ID = ?)";



    private Connection connection = null;

    public int delete(int transactionID) {
        PreparedStatement stmt = null;
        try {
                if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(DELETE);
            stmt.setInt(1, transactionID);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public Transaction get(int transactionID) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction(rs.getInt("Transaction_ID"), rs.getBigDecimal("Amount"),
                        rs.getInt("fromCreditCardID"), rs.getInt("toCreditCardID") );
                return transaction;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public List<Transaction> getOutGoingTransactionsForUser(int userID) {
        PreparedStatement stmt = null;
        List<Transaction> list = new ArrayList<Transaction>();

        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(OUTTRANSACTIONS);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(rs.getInt("Transaction_ID"), rs.getBigDecimal("Amount"),
                        rs.getInt("FROM_CreditCard_ID"), rs.getInt("TO_CreditCard_ID") );
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List<Transaction> getIncomingTransactionsForUser(int userID) {
        PreparedStatement stmt = null;
        List<Transaction> list = new ArrayList<Transaction>();

        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(INTRANSACTIONS);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(rs.getInt("Transaction_ID"), rs.getBigDecimal("Amount"),
                        rs.getInt("FROM_CreditCard_ID"), rs.getInt("TO_CreditCard_ID") );
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    public int update(Transaction transaction) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(UPDATE);
            stmt.setBigDecimal(1, transaction.getAmount());
            stmt.setInt(2, transaction.getFromCreditCardID());
            stmt.setInt(3, transaction.getToCreditCardID());
            stmt.setInt(4, transaction.getTransactionID());
            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public int insert(Transaction transaction) {

        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setBigDecimal(1, transaction.getAmount());
            stmt.setInt(2, transaction.getFromCreditCardID());
            stmt.setInt(3, transaction.getToCreditCardID());
            stmt.executeUpdate();
            ResultSet tableKeys = stmt.getGeneratedKeys();
            tableKeys.next();
            int autoGeneratedID = tableKeys.getInt(1);
            return autoGeneratedID;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

    public List<Transaction> getAll() {
        PreparedStatement stmt = null;
        List<Transaction> list = new ArrayList<Transaction>();

        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(rs.getInt("Transaction_ID"), rs.getBigDecimal("Amount"),
                        rs.getInt("fromCreditCardID"), rs.getInt("toCreditCardID") );
                list.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

}

