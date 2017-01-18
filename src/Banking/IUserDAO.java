package Banking;

import java.util.List;

/**
 * Created by tsytrin on 08.01.2017.
 */
public interface IUserDAO {
    public int delete(int accountID);
    public List<User> getAll();
    public User get(int accountID);
    public int insert(User user);
    public int update(User user);
}
