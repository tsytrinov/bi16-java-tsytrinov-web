package Banking;

/**
 * Created by tsytrin on 08.01.2017.
 */

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    //static reference to itself
    private static ConnectionManager instance = new ConnectionManager();
    private DataSource dataSource = null;

    private ConnectionManager() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("connpool");
        } catch (NamingException ex) {
            System.err.println ("Cannot connect to database server");
            ex.printStackTrace();
        }

    }

    private Connection createConnection() {
        Connection connection = null;
        if (dataSource != null)
        try {
            connection = dataSource.getConnection();
        } catch (SQLException ex) {
            System.err.println ("Cannot take a new connection from pool");
            ex.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnection() {

        if(instance == null) {
            instance = new ConnectionManager();
        }
        return instance.createConnection();
    }
}
