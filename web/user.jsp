<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User page</title>
    </head>
    <body>
        <%
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
            
            if (loggedIn != null && loggedIn == true) {
                String username = (String) session.getAttribute("username");
        %>
        
        <p><%out.println("Hello, " + username);%></p>

        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="showQuizzes">
            <input type="submit" value="Take a quiz">
        </form> 
        <br>
        
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="showResults">
            <input type="submit" value="Show previous results">
        </form> 
        <br>
        
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="logout">
            <input type="submit" value="Logout">
        </form> 

        <%
            } else {
                out.println("Unauthorized please login!");
            }
        %>
    </body>
</html>
