package Banking;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class User {

    private int userID;
    private String firstName;
    private String lastName;
    private String country;
    private String email;
    private String password;

    public User(int userID, String firstName, String lastName, String country, String email, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.password = password;
    }

    public User(int userID, String firstName, String lastName, String country, String email) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.email = email;
        this.password = null;
    }

    public int getUserID(){return userID;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getCountry(){return country;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
}
