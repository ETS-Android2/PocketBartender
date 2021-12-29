package edu.cmu.abhinaas.project4task2;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "logs", value = "/logs")
public class LogsServlet extends HttpServlet {
    CocktailRecipeMongo cmongo;

    /**
     * Always gets called to ensure that the model is ready and avilable
     */
    public void init() {
        this.cmongo = new CocktailRecipeMongo();
    }

    /**
     * This is the get method that gets the information regarding requestLogs\
     * and responseLogs from the mongodb model and displays it on the logs.jsp page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> requestLogs = cmongo.getRequest();
        request.setAttribute("requestLogs", requestLogs);
        List<Map<String, String>> responseLogs = cmongo.getResponse();
        request.setAttribute("responseLogs", responseLogs);
        String nextView = "logs.jsp";
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
