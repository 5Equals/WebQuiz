<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome the quiz login page</title>
    </head>
    <body>
        <h3>Please login:</h3>

        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <label for="username">Username:</label><br>
            <input type="text" id="username" name="username" placeholder="example@kth.se"><br><br>
            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password" placeholder="password1234"><br><br>
            <input type="hidden" name="action" value="login">
            <input type="submit" value="Login">
        </form> 

        <%
            Boolean isInvalidLogin = (Boolean) session.getAttribute("invalidLogin");
            
            if (isInvalidLogin != null && isInvalidLogin == true) {
                out.println("<p style='color:red;'>Invalid login credentials, please try again!</p>");
                session.setAttribute("invalidLogin", (Boolean) false);
                session.setAttribute("loggedIn", (Boolean) false);
                session.setAttribute("username", null);
            }
        %>
    </body>
</html>
