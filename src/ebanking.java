import Banking.*;
import com.github.javafaker.Faker;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tsytrin on 08.01.2017.
 */
public class ebanking extends javax.servlet.http.HttpServlet {

    static final int NUM_ACCOUNTS = 100;
    static final int MAX_CREDIT_CARDS_PER_ACCOUINT = 10;
    static final int MAX_TRANSACTIONS_PER_CARD = 20;
    static final BigDecimal MAX_AMOUNT = new BigDecimal(100000 + ".00");
    static final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);


    static Faker faker = new Faker(new Locale("en"));

    static List<Integer> generateUsers() {

        List<Integer> generatedRowsIDs = new ArrayList<>();
        UserDAOImpl userDAO = daoFactory.getUserDAO();
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            com.github.javafaker.Name fakeName = faker.name();
            String email = faker.internet().emailAddress();
            String tmpPasswd = /*fakeName.firstName() + "_" +*/ "!"+email;
            String hashed = BCrypt.hashpw(tmpPasswd, BCrypt.gensalt());
            User user = new User(0, fakeName.firstName(), fakeName.lastName(), faker.address().countryCode(), email, hashed);
            generatedRowsIDs.add(userDAO.insert(user));
        }
        return generatedRowsIDs;
    }


    public static BigDecimal generateRandomBigDecimalFromRange(BigDecimal min, BigDecimal max) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    static List<Integer> generateCards(List<Integer> userIDs) {
        List<Integer> generatedCards = new ArrayList<>();
        CreditCardDAOImpl cardDAO = daoFactory.getCreditCardDAO();

        for (int userID : userIDs) {
            int numCards = ThreadLocalRandom.current().nextInt(0, MAX_CREDIT_CARDS_PER_ACCOUINT + 1);

            for (int i = 0; i < numCards; i++) {
                BigDecimal actualRandomAmount = generateRandomBigDecimalFromRange(new BigDecimal(0), MAX_AMOUNT);
                CreditCard creditCard = new CreditCard(0, actualRandomAmount, userID);
                generatedCards.add(cardDAO.insert(creditCard));
            }
        }
        return generatedCards;
    }

    static List<Integer> generateTransactions(List<Integer> generatedCards) {
        List<Integer> transactions = new ArrayList<>();
        CreditCardDAOImpl cardDAO = new CreditCardDAOImpl();
        TransactionDAOImpl transactionDAO = daoFactory.getTransactionDAO();

        for (int cardID : generatedCards) {
            CreditCard card = cardDAO.get(cardID);
            if (card != null) {
                BigDecimal maxCardTransactionAmount = card.getCashAmount();
                int numTransaction = ThreadLocalRandom.current().nextInt(0, MAX_TRANSACTIONS_PER_CARD + 1);
                for (int i = 0; i < numTransaction; i++) {
                    BigDecimal transactionCandidate = generateRandomBigDecimalFromRange(new BigDecimal(0), maxCardTransactionAmount);
                    int recipientCandidate = generatedCards.get(ThreadLocalRandom.current().nextInt(0, generatedCards.size()));
                    if (recipientCandidate != cardID) {
                        Transaction transaction = new Transaction(0, transactionCandidate, cardID, recipientCandidate);
                        transactions.add(transactionDAO.insert(transaction));
                        maxCardTransactionAmount = maxCardTransactionAmount.subtract(transactionCandidate);
                        if (maxCardTransactionAmount.compareTo(new BigDecimal(0)) < 1)
                            i = numTransaction;
                    }
                }
            } else {
                System.err.println("Generation, card is not found");
            }
        }
        return generatedCards;
    }


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getParameter("action") != null) {
            if (request.getParameter("action").compareTo("login") == 0) {
                UserDAOImpl userDAO = daoFactory.getUserDAO();
                String userName = request.getParameter("username");
                String userPass = request.getParameter("userpass");
                User user = userDAO.validate(userName, userPass);
                if (user == null)
                    response.sendRedirect("/?error=login");
                else {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser",user);
                    response.sendRedirect("userLogged.jsp");
                }
            }
        }


    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        // Use this URL to generate users: http://localhost:8080/ebanking?generateSampleUsers=true
        if ((request.getParameter("generateSampleUsers") != null) && (request.getParameter("generateSampleUsers").compareTo("true") == 0)) {
            List<Integer> accountsIDs = generateUsers();
            List<Integer> cardsIDs = generateCards(accountsIDs);
            List<Integer> transactions = generateTransactions(cardsIDs);
            response.getWriter().print("<HTML><H1>There were generated: " + accountsIDs.size() + " accounts, " + cardsIDs.size() +
                    " cards, " + transactions.size() + " transactions </H1></HTML>");
        }


    }

}

/*
BCrypt.checkpw(candidate, hashed)
DELETE FROM `bi16-java-web`.Transaction;
DELETE FROM `bi16-java-web`.CreditCard;
DELETE FROM `bi16-java-web`.User;

 */