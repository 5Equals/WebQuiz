<%-- 
    Document   : result
    Created on : 22 Nov 2021, 20:37:23
    Author     : Simon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your quiz result</title>
    </head>
    <body>
        <%
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
            if (loggedIn != null) {
                if (loggedIn == true) {
                    String username = (String) session.getAttribute("username");
                    String subject = (String) session.getAttribute("subject");
        %>

        <h3>You (<% out.print(username); %>) have scored: <%
            Integer score = (Integer) session.getAttribute("quizScore");
            Integer totalScore = (Integer) session.getAttribute("quizTotalScore");
            out.print(score + " out of " + totalScore + " in the " + subject + " quiz.");
            %>
        </h3>
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="goToUserPage">
            <input type="submit" value="Go to my user page">
        </form> 

        <%
                } else {
                    out.println("Unauthorized please login!");
                }

            } else {
                out.println("Unauthorized please login!");
            }

        %>

    </body>
</html>
