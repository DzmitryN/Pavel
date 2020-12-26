package controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddNewStudentMainController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //String path = request.getRequestURI().replace("/Portal/", "");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/Views/AddNewStudentMain.jsp");
        requestDispatcher.forward(request, response);
    }
}
