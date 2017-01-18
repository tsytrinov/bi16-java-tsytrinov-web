package Banking;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class UserDAOImpl implements IUserDAO {

    private static final String DELETE = "DELETE FROM User WHERE User_ID=?";
    private static final String FIND_ALL = "SELECT * FROM User ORDER BY User_ID";
    private static final String FIND_BY_ID = "SELECT * FROM User WHERE User_ID=?";
    private static final String VALIDATE = "SELECT * FROM User WHERE Email=?";

    private static final String INSERT = "INSERT INTO User(FirstName, LastName, Country, Email, Password) VALUES(?, ?, ?, ?,?)";
    private static final String UPDATE = "UPDATE User SET FirstName=?, LastName=?, Country=?, Email=?, Password=? WHERE User_ID=?";

    private Connection connection = null;

    public int delete(int userID) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(DELETE);
            stmt.setInt(1, userID);
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

    public User get(int userID) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getInt("User_ID"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Country"), rs.getString("email"));
                return user;
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

    public User validate(String userName, String password) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(VALIDATE);
            stmt.setString(1, userName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                if (BCrypt.checkpw(password, rs.getString("Password")))
                return new User(rs.getInt("User_ID"), rs.getString("FirstName"),
                            rs.getString("LastName"), rs.getString("Country"), rs.getString("email"));
                return null;
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

    public int update(User user) {
        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(UPDATE);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getCountry());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());

            stmt.setInt(6, user.getUserID());
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

    public int insert(User user) {

        PreparedStatement stmt = null;
        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getCountry());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());

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

    public List<User> getAll() {
        PreparedStatement stmt = null;
        List<User> list = new ArrayList<>();

        try {
            if (connection == null || !connection.isValid(1)) {
                connection = ConnectionManager.getConnection();
            }
            stmt = connection.prepareStatement(FIND_ALL);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("User_ID"), rs.getString("FirstName"),
                        rs.getString("LastName"), rs.getString("Country"), rs.getString("email"));
                list.add(user);
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
