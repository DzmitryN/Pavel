package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorController {
    public void openPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String dir = request.getRequestURI();
        String path = dir.replaceAll(dir, "");

        request.getRequestDispatcher( path + "WEB-INF/Views/Error.jsp").forward(request, response);
    }
}
