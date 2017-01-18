<%--
  Created by IntelliJ IDEA.
  User: tsytrin
  Date: 08.01.2017
  Time: 8:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Login page</title>
  </head>
  <body>
  <% if (request.getParameter("error") == "login")  %>
  <% String user = request.getParameter("error");
    if( user != null && user.compareTo("login") ==0 ) {
  %>
  <h1 style="color:red;">Provided Credentials are wrong. Please try again</h1>
  <%
  }
  %>


  <form action="/ebanking" method="post">
      <input type="hidden" name="action" value="login">
      Login:<input type="text" name="username"/><br/><br/>
      Password:<input type="password" name="userpass"/><br/><br/>
      <input type="submit" value="login"/>
  </form>

  </body>
</html>
