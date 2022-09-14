<%@page import="dto.QuizDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quizzes</title>
    </head>
    <body>
        <%
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

            if (loggedIn != null && loggedIn == true) {
                QuizDTO[] quizzes = (QuizDTO[]) session.getAttribute("quizzes");
                String username = (String) session.getAttribute("username");
        %>

        <p>Choose a quiz to take: (<%out.print(username); %>)</p>

        <%
                for (QuizDTO quiz : quizzes) {
                    out.print(quiz);
        %>

        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="takeQuiz">
            <input type="hidden" name="nr" value="<%out.print(quiz.getQuizId()); %>">
            <input type="submit" value="Take Quiz">
        </form>
        <br>

        <%
                }
        %>
        
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="goToUserPage">
            <input type="submit" value="Go to my user page">
        </form>

        <%
            } else {
                out.println("Unauthorized please login!");
            }
        %>
    </body>
</html>
