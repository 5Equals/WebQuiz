<%@page import="dto.ResultDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User results</title>
    </head>
    <body>
        <%
            Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
            
            if (loggedIn != null && loggedIn == true) {
                String username = (String) session.getAttribute("username");
        %>
        
        <p><%out.println(String.format("%s's previous results:", username));%></p>
        
        <%
            ResultDTO[] resultDTOs = (ResultDTO[]) session.getAttribute("results");
            
            if (resultDTOs != null) {
                for (ResultDTO resultDTO : resultDTOs) {
        %>
        <p>
            <%
                    out.println(resultDTO);
            %>
        </p>   
        <%
                }
            } else {
                out.println("No reults found.");
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
