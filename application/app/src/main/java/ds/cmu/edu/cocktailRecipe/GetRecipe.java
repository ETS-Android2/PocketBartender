package ds.cmu.edu.cocktailRecipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class GetRecipe {
    CocktailRecipe ip = null;


    public void search(String searchTerm, CocktailRecipe ip) {
        this.ip = ip;
        new AsyncFlickrSearch().execute(searchTerm);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncFlickrSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... searchRecipe) {
            try {
                return searchRecipe(searchRecipe[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String recipeJSON) {
            ip.recipeReady(recipeJSON);
        }


        private String searchRecipe(String searchTerm) throws IOException, JSONException {
            String cocktailName = searchTerm;

            String url = "https://boiling-sands-55869.herokuapp.com/fetch-recipe?name="+cocktailName;
            //https://www.thecocktaildb.com/api/json/v1/1/search.php?s=
            //https://www.boiling-sands-55869.herokuapp.com/fetch-recipe?name=
            StringBuilder result = new StringBuilder();
            URL url_2 = new URL(url);
            HttpURLConnection con = (HttpURLConnection) url_2.openConnection();
            con.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }

            JSONObject jsonObject = new JSONObject(result.toString().trim().replaceAll("null", "\"none\""));



            System.out.println("Name: " + jsonObject.getString("Name"));
            System.out.println("Type of Drink: " + jsonObject.getString("Type of Drink"));
            System.out.println("Recipe: " + jsonObject.getString("Recipe"));
            System.out.println("----------------------------------------------------------------");


            StringBuilder responseOutputString = new StringBuilder();
            responseOutputString.append("Name: "+ jsonObject.getString("Name"));
            responseOutputString.append(System.getProperty("line.separator"));
            responseOutputString.append("Type of Drink: "+ jsonObject.getString("Type of Drink"));
            responseOutputString.append(System.getProperty("line.separator"));
            responseOutputString.append("Recipe: "+ jsonObject.getString("Recipe"));

            return responseOutputString.toString();


        }
    }
}