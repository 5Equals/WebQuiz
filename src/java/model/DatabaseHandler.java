package model;

import dto.QuestionDTO;
import dto.QuizDTO;
import dto.ResultDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Responsible for handling the communication to the database, 
// and executing queries to retrieve data and save data to the database.
public class DatabaseHandler {

    Connection connection = null;
    Statement statment = null;

    // Get the connection to the database if it exists.
    private Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/webapplicationdb", "postgres", "postgres");
            connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to the Database.");
        }

        return connection;
    }

    // Establish the connection to the database and prepare the tools needed to executed queries.
    public void establishConnection() {
        try {
            connection = getConnection();
            statment = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not communicate with the Database.");
        }
    }

    // Check if the user login information whether the user exists or have the right information.
    public boolean checkLogin(String username, String password) {
        String query = "SELECT	users.id\n"
                + "FROM	users\n"
                + "WHERE	users.username LIKE '" + username + "' "
                + "AND users.password = md5('" + password + "')";

        ResultSet res = runQuery(query);

        try {
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }

        return false;
    }

    // Get all the quizzes that are recorded in the database.
    public QuizDTO[] getQuizzes() {
        List<QuizDTO> quizzes = new ArrayList<QuizDTO>();

        //Dictionary<Integer, String> quizzes = new Hashtable<>();
        String query = "SELECT   *\n"
                + "FROM     quizzes\n"
                + "ORDER BY quizzes.id ASC";

        ResultSet res = runQuery(query);

        try {
            while (res.next()) {
                QuizDTO quiz = new QuizDTO();
                Integer id = Integer.parseInt(res.getString(1));
                String subject = res.getString(2);

                quiz.setQuizId(id);
                quiz.setQuizSubject(subject);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            return null;
        }

        return quizzes.toArray(new QuizDTO[0]);
    }

    // Get the questions that relate to a specific quiz.
    public QuestionDTO[] getQuestions(int quizId) {
        List<QuestionDTO> questions = new ArrayList<QuestionDTO>();

        String query = "SELECT      questions.*\n"
                + "FROM        questions\n"
                + "INNER JOIN  selector ON (selector.question_id = questions.id)\n"
                + "WHERE	    selector.quiz_id = " + quizId
                + "ORDER BY questions.id ASC";

        ResultSet res = runQuery(query);

        try {
            while (res.next()) {
                QuestionDTO question = new QuestionDTO();
                
                Integer id = Integer.parseInt(res.getString(1));
                String text = res.getString(2);
                String options = res.getString(3);
                String answer = res.getString(4);
                
                question.setQuestionId(id);
                question.setQuestionText(text);
                question.setQuestionOptions(options);
                question.setQuestionAnswers(answer);
                
                questions.add(question);
            }
        } catch (SQLException e) {
            return null;
        }

        return questions.toArray(new QuestionDTO[0]);
    }

    // Get the previous results recored for the user.
    public ResultDTO[] getResults(String username) {
        List<ResultDTO> results = new ArrayList<ResultDTO>();

        String query = "SELECT     results.id, results.quiz_id, score\n"
                + "FROM       results\n"
                + "INNER JOIN users ON (users.id = results.user_id)\n"
                + "WHERE	   users.username LIKE '" + username + "'\n"
                + "ORDER BY   results.id ASC";

        ResultSet res = runQuery(query);

        try {
            while (res.next()) {
                ResultDTO result = new ResultDTO();
                
                Integer id = Integer.parseInt(res.getString(1));
                Integer quizId = Integer.parseInt(res.getString(2));
                Integer score = Integer.parseInt(res.getString(3));

                result.setResultId(id);
                result.setSubject(getQuizSubject(quizId));
                result.setResultScore(score);

                results.add(result);
            }
        } catch (SQLException e) {
            return null;
        }

        return results.toArray(new ResultDTO[0]);
    }

    // Save the result of a quiz for a specific user.
    public boolean saveResults(String username, int quizId, int score) {
        String userIdQuery = "SELECT  	users.id\n"
                + "FROM    	users\n"
                + "WHERE	users.username LIKE '" + username + "'";

        ResultSet resUserId = runQuery(userIdQuery);
        int userId;

        try {
            while (resUserId.next()) {
                userId = Integer.parseInt(resUserId.getString(1));
                String insertQuery = "INSERT INTO results(user_id, quiz_id, score)\n"
                        + "VALUES (" + userId + ", " + quizId + " , " + score + ");";

                statment.executeUpdate(insertQuery);
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    // Get the subject of a specific quiz.
    public String getQuizSubject(int quizId) {
        String subjectQuery = "SELECT	quizzes.subject\n"
                + "FROM	quizzes\n"
                + "WHERE	quizzes.id = " + quizId;

        ResultSet subjectRes = runQuery(subjectQuery);

        try {
            while (subjectRes.next()) {
                String quizSubject = subjectRes.getString(1);
                return quizSubject;
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    // Run the query that is passed and return the results.
    private ResultSet runQuery(String query) {
        try {
            ResultSet res = statment.executeQuery(query);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong when executing the query.");
            return null;
        }
    }
}
