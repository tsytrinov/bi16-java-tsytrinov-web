<%@ page import="Banking.User" %>
<%@ page import="Banking.TransactionDAOImpl" %>
<%@ page import="Banking.DAOFactory" %>
<%@ page import="Banking.Transaction" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: tsytrin
  Date: 08.01.2017
  Time: 8:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <% User user = (User)request.getSession().getAttribute("currentSessionUser");
       String userFirst = user.getFirstName();
       String userLast = user.getLastName();
       final DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
       TransactionDAOImpl transactionDAO = daoFactory.getTransactionDAO();
       List<Transaction> incomingTransactions =  transactionDAO.getIncomingTransactionsForUser(user.getUserID());
       List<Transaction> outgoingTransactions =  transactionDAO.getOutGoingTransactionsForUser(user.getUserID());
       pageContext.setAttribute("incomingTransactions",incomingTransactions);
       pageContext.setAttribute("outgoingTransactions",outgoingTransactions);

    %>

    <title>Transaction Center</title>
    <h3>Hi <%=userLast%> <%=userFirst%>!</h3>
    <h4>Your incoming transactions:</h4>
    <table>
        <tr><th>Amount</th><th>Debit</th></tr>
    <c:forEach items="${incomingTransactions}" var="incomingTransaction">
        <tr>
            <td><c:out value="${incomingTransaction.getAmount()}"/></td>
            <td><c:out value="${incomingTransaction.getFromCreditCardID()}"/></td>
        </tr>
    </c:forEach>
    </table>

    <h4>Your outgoing transactions:</h4>
    <table>
        <tr><th>Amount</th><th>Credit</th></tr>
        <c:forEach items="${outgoingTransactions}" var="incomingTransaction">
            <tr>
                <td><c:out value="${incomingTransaction.getAmount()}"/></td>
                <td><c:out value="${incomingTransaction.toCreditCardID}"/></td>
            </tr>
        </c:forEach>
    </table>

</head>
<body>

</body>
</html>
