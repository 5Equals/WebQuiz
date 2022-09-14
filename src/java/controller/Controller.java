/**
 * A quiz game with questions and choices. The game purpose is to solve the questions of the quizzes right.
 */

package controller;

import dto.QuestionDTO;
import dto.QuizDTO;
import dto.ResultDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import model.DatabaseHandler;

// Responsible for the requests coming from the client, and handles the get and post requests
// accordantly to the proper situation.
public class Controller extends HttpServlet {

    private DatabaseHandler databaseHandler;

    public Controller() {
        databaseHandler = new DatabaseHandler();
        databaseHandler.establishConnection();
    }

    // Handle the get request coming from the client.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            rd = request.getRequestDispatcher("/user.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }
        rd.forward(request, response);
    }

    // Handle the post request coming from the client and redirect them to the proper page.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);

        if (request.getParameter("action").equals("login")) {
            processLogin(request, response, session);
        } else if (request.getParameter("action").equals("showResults")) {
            processShowResults(request, response, session);
        } else if (request.getParameter("action").equals("logout")) {
            processLogout(request, response, session);
        } else if (request.getParameter("action").equals("goToUserPage")) {
            processGoToUserPage(request, response, session);
        } else if (request.getParameter("action").equals("showQuizzes")) {
            processQuizzes(request, response, session);
        } else if (request.getParameter("action").equals("takeQuiz")) {
            processTakingQuiz(request, response, session);
        } else if (request.getParameter("action").equals("submitQuiz")) {
            processSubmitQuiz(request, response, session);
        }
    }

    // Process the response page to redirect for login page.
    void processLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean result = databaseHandler.checkLogin(username, password);
        RequestDispatcher rd;

        if (result == true) {
            session.setAttribute("invalidLogin", (Boolean) false);
            session.setAttribute("loggedIn", (Boolean) true);
            session.setAttribute("username", username);
            rd = request.getRequestDispatcher("/user.jsp");
        } else {
            session.setAttribute("invalidLogin", (Boolean) true);
            rd = request.getRequestDispatcher("/index.jsp");
        }

        rd.forward(request, response);
    }

    // Process the response page to logout the user.
    void processLogout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            session.setAttribute("username", null);
            session.setAttribute("loggedIn", (Boolean) false);
            session.setAttribute("quizId", null);
        }

        rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    // Process the response page to redirect for the page showing the previous results.
    void processShowResults(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            String username = (String) session.getAttribute("username");
            ResultDTO[] results = databaseHandler.getResults(username);
            session.setAttribute("results", results);
            rd = request.getRequestDispatcher("/results.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }

        rd.forward(request, response);
    }

    // Process the response page to redirect for the page listing all the available quizzes.
    void processQuizzes(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            QuizDTO[] quizzes = databaseHandler.getQuizzes();
            session.setAttribute("quizzes", quizzes);
            rd = request.getRequestDispatcher("/quizzes.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }

        rd.forward(request, response);
    }

    // Process the response page to redirect for the page of having a quiz chosen by the client.
    void processTakingQuiz(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            int quizId = Integer.parseInt(request.getParameter("nr"));
            QuestionDTO[] questions = databaseHandler.getQuestions(quizId);
            session.setAttribute("questions", questions);
            session.setAttribute("quizId", quizId);
            session.setAttribute("subject", databaseHandler.getQuizSubject(quizId));
            rd = request.getRequestDispatcher("/quiz.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }

        rd.forward(request, response);
    }

    // Process the response page to redirect for the page showing the result of the submitted quiz.
    void processSubmitQuiz(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            String username = (String) session.getAttribute("username");
            Integer quizId = (Integer) session.getAttribute("quizId");
            QuestionDTO[] questionDTOs = (QuestionDTO[]) session.getAttribute("questions");
            int score = gradeQuizAnswers(questionDTOs, request);
            int totalScore = questionDTOs.length;
            databaseHandler.saveResults(username, quizId, score);
            session.setAttribute("quizScore", score);
            session.setAttribute("quizTotalScore", totalScore);
            rd = request.getRequestDispatcher("/result.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }
        rd.forward(request, response);
    }
    
    // Process the user answers for the submitted quiz and grade the answers of the user.
    int gradeQuizAnswers(QuestionDTO[] questions, HttpServletRequest request) {
        List<String> answers = new ArrayList<String>();
        
        int score = 0;
        
        for (int questionNumber = 0; questionNumber < questions.length; questionNumber++) {
            StringBuilder answer = new StringBuilder();
            for (int optionNumber = 0; optionNumber < 3; optionNumber++) {
                String optionValue = request.getParameter(questionNumber + "-" + optionNumber);
                if (optionValue != null && optionValue.equals("on")) {
                    answer.append("1");
                } else {
                    answer.append("0");
                }
                if (optionNumber != 2) {
                    answer.append("/");
                }
            }
            answers.add(answer.toString());
        }

        String[] userAnswers = answers.toArray(new String[0]);
        
        for (int questionNumber = 0; questionNumber < questions.length; questionNumber++) {
            String correctAnswer = questions[questionNumber].getQuestionAnswers();
            String userAnswer = userAnswers[questionNumber];

            if (userAnswer.equals(correctAnswer)) {
                score++;
            }
        }

        return score;
    }

    // Process the response page redirecting to the user page.
    void processGoToUserPage(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {

        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        RequestDispatcher rd;

        if (loggedIn != null && loggedIn == true) {
            rd = request.getRequestDispatcher("/user.jsp");
        } else {
            rd = request.getRequestDispatcher("/index.jsp");
        }

        rd.forward(request, response);
    }
}
