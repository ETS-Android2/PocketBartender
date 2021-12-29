package edu.cmu.abhinaas.project4task2;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This is the Analytics Information display servlet
 * This displays 3 analytical information points regarding
 * data requested to/ responded by the web application
 */
@WebServlet(name = "analytics", value = "/analytics")
public class AnalyticsServlet extends HttpServlet {
    CocktailRecipeMongo cmongo;

    /**
     * Always gets called to ensure that the model is ready and avilable
     */
    public void init() {
        this.cmongo = new CocktailRecipeMongo();
    }

    /**
     * This is the get method that gets the information regarding getMostRecentCocktailsMapList, mostSearchedDrinkMap
     * and requestCount from the mongodb model and displays it on the analytics.jsp page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Map<String, String>> getMostRecentCocktailsMapList = cmongo.getMostRecentCocktails();
        request.setAttribute("getMostRecentCocktailsMapList", getMostRecentCocktailsMapList);

        Map<String, String> mostSearchedDrinkMap = cmongo.getMostSearchedForCocktail();
        request.setAttribute("mostSearchedDrinkMap", mostSearchedDrinkMap);

        String requestCount = cmongo.getTotalNumberOfRequests();
        request.setAttribute("requestCount", requestCount);


        String nextView = "analytics.jsp";
        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
