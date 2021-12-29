package edu.cmu.abhinaas.project4task2;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is the servlet that is called
 * by the android app and calls the 3rd party api
 * to display the cocktail's recipe.
 */
@WebServlet(name = "FetchRecipeServlet",
        urlPatterns = {"/fetch-recipe"})
public class FetchRecipeServlet extends HttpServlet {
    CocktailRecipeMongo cmongo;

    /**
     * Always gets called to ensure that the model is ready and avilable
     */
    @Override
    public void init() {
        cmongo = new CocktailRecipeMongo();
    }

    /**
     * This is the get method that accepts the request from the user and sends that data to the mongodb model
     * to be stored in the db. After that, it processes the request and sends a request to the 3rd party
     * cocktaildb api and receives the response. It then processes the response and stores the response
     * information in the mongodb database via the model.
      * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {


        String authScheme = request.getAuthType();
        String servletPath = request.getServletPath();
        boolean isSecure = request.isSecure();
        String browserName = request.getHeader("User-Agent");
        String ipOfMachine = request.getRemoteAddr();

        String cocktailName = request.getParameter("name");

        String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="+cocktailName;

        StringBuilder stringResult = new StringBuilder();
        URL urlToHit = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlToHit.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            for (String line; (line = bufferedReader.readLine()) != null; ) {
                stringResult.append(line);
            }
        }
        JSONObject jsonORequestbject = new JSONObject();
        jsonORequestbject.put("AuthenticationScheme", authScheme);
        jsonORequestbject.put("Path", servletPath);
        jsonORequestbject.put("RequestSecure", isSecure);
        jsonORequestbject.put("BrowserName", browserName);
        jsonORequestbject.put("MachineIP", ipOfMachine);
        jsonORequestbject.put("CurrentTimeStamp", getCurrentTimeStamp());


        cmongo.insertRequest(jsonORequestbject.toString());


        JSONObject jsonObject = new JSONObject(stringResult.toString().trim().replaceAll("null", "\"none\""));
        JSONArray cocktailArray = jsonObject.getJSONArray("drinks");
        JSONObject firstCocktail = cocktailArray.getJSONObject(0);


        System.out.println("Name: " + firstCocktail.getString("strDrink"));
        System.out.println("Type of Drink: " + firstCocktail.getString("strCategory"));
        System.out.println("Recipe: " + firstCocktail.getString("strInstructions"));
        System.out.println("----------------------------------------------------------------");

        JSONObject responseOutput = new JSONObject();
        responseOutput.put("Name", firstCocktail.getString("strDrink"));
        responseOutput.put("Type of Drink", firstCocktail.getString("strCategory"));
        responseOutput.put("Recipe", firstCocktail.getString("strInstructions"));
        responseOutput.put("CurrentTimeStamp", getCurrentTimeStamp());


        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(responseOutput.toString());
        out.flush();

        cmongo.insertResponse(responseOutput.toString());

    }


    // Credit: https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java

    /**
     * Generates the current timestamp
     * @return
     */
    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}

