<%@page import="dto.QuestionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taking quiz</title>
    </head>
    <body>
        <%
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
            
            if (loggedIn != null && loggedIn == true) {
                Integer quizId = (Integer) session.getAttribute("quizId");
                
                if (quizId != null) {
                    String username = (String) session.getAttribute("username");
                    String subject = (String) session.getAttribute("subject");
                    QuestionDTO[] questions = (QuestionDTO[]) session.getAttribute("questions");
        %>
        
        <p>Taking the quiz about <%out.println(subject);%>: (<% out.print(username); %>)</p>
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            
            <%
                    for (int questionNumber = 0; questionNumber < questions.length; questionNumber++) {
                        QuestionDTO question = questions[questionNumber];
                        out.print((questionNumber + 1) + ". " + question.getQuestionText() + "<br>");
                        String[] options = question.getQuestionOptions().split("/");
                        int optionNumber = 0;
                        for (String option : options) {
            %>

            <div>
                <input type="checkbox" id="<% out.print(questionNumber + "-" + optionNumber); %>" 
                       name="<% out.print(questionNumber + "-" + optionNumber); %>" unchecked>
                <label for="<% out.print(questionNumber + "-" + optionNumber++); %>"><% out.print(option); %></label>
            </div>
            
            <%
                        }
                        out.print("<br>");
                    }
            %>

            <input type="hidden" name="action" value="submitQuiz">
            <input type="submit" value="submit quiz">
        </form>
        <br>

        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="goToUserPage">
            <input type="submit" value="Go to my user page">
        </form>
        
        <%
                } else {
        %>
        <h3>You have not chosen any quiz, please go back to user page and choose a quiz first.</h3>
        <form method="post" action="/ID1212Task3WebApplication/Controller">
            <input type="hidden" name="action" value="goToUserPage">
            <input type="submit" value="Go to my user page">
        </form>
        
        <%
                }
            } else {
                out.println("Unauthorized please login!");
            }
        %>
    </body>
</html>
