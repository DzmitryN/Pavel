package controllers;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorController {
    public void OpenPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String dir = request.getRequestURI();
        String path = dir.replaceAll(dir, "");

        RequestDispatcher requestDispatcher = request.getRequestDispatcher( path + "WEB-INF/Views/Error.jsp");
        requestDispatcher.forward(request, response);
    }
}
