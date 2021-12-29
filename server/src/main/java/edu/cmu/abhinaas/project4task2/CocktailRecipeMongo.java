package edu.cmu.abhinaas.project4task2;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;

import javax.print.Doc;
import java.sql.SQLOutput;
import java.util.*;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

/**
 * This class is the model that interacts with mongodb atlas on the cloud.
 * This helps get data to display on the main cocktail recipes page as well as
 * for the dashboard (analytics and logs pages). It also inserts data into mongodb
 * whenever the 3rd party api is hit through this web application (which also means when this web application
 * is sent a request by the user).
 */
public class CocktailRecipeMongo {
    static MongoDatabase database;

    /**
     * This is the constructor that forms the connection based on the connection string
     * extracted from the mongodb instance on the cloud.
     * It also creates the Collection called CocktailRecipes.
     */
    CocktailRecipeMongo(){
        ConnectionString connectionString = new ConnectionString("mongodb://abhinaas:abhinaas@cluster0-shard-00-00.xwgjp.mongodb.net:27017,cluster0-shard-00-01.xwgjp.mongodb.net:27017,cluster0-shard-00-02.xwgjp.mongodb.net:27017/myFirstDatabase?ssl=true&replicaSet=atlas-6awi6u-shard-0&authSource=admin&retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("CocktailRecipes");
    }


    /**
     * This method gets the request data inserted into the mongodb collection
     * @return
     */
    public List<Map <String, String>> getRequest() {
        MongoCollection<Document> collection = database.getCollection("request");
        FindIterable<Document> documentFindIterable = collection.find().sort(Sorts.ascending("CurrentTimeStamp"));
        List<Map <String, String>> listOfDocuments = new ArrayList<>();
        for (Document document : documentFindIterable){
            Map<String, String> docMap = new HashMap<String, String>();
            docMap.put("AuthenticationScheme", String.valueOf(document.get("AuthenticationScheme")));
            docMap.put("Path", String.valueOf(document.get("Path")));
            docMap.put("RequestSecure", String.valueOf(document.get("RequestSecure")));
            docMap.put("BrowserName", String.valueOf(document.get("BrowserName")));
            docMap.put("MachineIP", String.valueOf(document.get("MachineIP")));
            docMap.put("CurrentTimeStamp", String.valueOf(document.get("CurrentTimeStamp")));
            listOfDocuments.add(docMap);
        }
        return listOfDocuments;
    }


    /**
     * This method gets the response data inserted into the mongodb collection
     * @return
     */
    public List<Map <String, String>> getResponse() {
        MongoCollection<Document> collection = database.getCollection("response");
        FindIterable<Document> iterator = collection.find().sort(Sorts.ascending("CurrentTimeStamp"));
        List<Map <String, String>> listOfDocuments = new ArrayList<>();
        for (Document document : iterator){
            Map<String, String> docMap = new HashMap<String, String>();
            docMap.put("TypeofDrink", String.valueOf(document.get("Type of Drink")));
            docMap.put("Recipe", String.valueOf(document.get("Recipe")));
            docMap.put("Name", String.valueOf(document.get("Name")));
            docMap.put("CurrentTimeStamp", String.valueOf(document.get("CurrentTimeStamp")));
            listOfDocuments.add(docMap);
        }
        return listOfDocuments;
    }


    /**
     * This gets the total number of requests sent to the webapplication
     * and consequently to the 3rd party api
     * @return
     */
    public String  getTotalNumberOfRequests() {
        String requestCount = null;
        MongoCollection<Document> collection = database.getCollection("request");
        Document totalRequestsDoc = collection.aggregate(Arrays.asList(
                count())).first();
        requestCount = String.valueOf(totalRequestsDoc.get("count"));
        return requestCount;
    }


    /**
     * This gets the 3 most recently requested cocktail recipes
     * with their reqpective timestamps.
     * @return
     */
    public List<Map <String, String>> getMostRecentCocktails() {
        List<Map <String, String>> listOfMaps = new ArrayList<>();

        MongoCollection<Document> collection = database.getCollection("response");

        collection.aggregate(Arrays.asList(sort(Sorts.descending("CurrentTimeStamp")),
                limit(3), out("mostCommon"))).toCollection();

        MongoCollection<Document> mostCommonCocktail = database.getCollection("mostCommon");
        FindIterable<Document> iterable = mostCommonCocktail.find();
        for (Document document : iterable){
            Map<String, String> mostRecentDrinkMap = new TreeMap<>();
            mostRecentDrinkMap.put("Name", String.valueOf(document.get("Name")));
            mostRecentDrinkMap.put("CurrentTimeStamp", String.valueOf(document.get("CurrentTimeStamp")));
            listOfMaps.add(mostRecentDrinkMap);
        }

        return listOfMaps;
    }


    /**
     * This method gets the most requested for cocktail recipe and the
     * number of times it was requested for.
     * @return
     */
    public Map<String, String>  getMostSearchedForCocktail() {
        Map<String, String> mostSearchedDrinkMap = new HashMap<>();
        String mostSearchedDrink = null;
        String mostSearchedDrinkFrequency = null;

        MongoCollection<Document> collection = database.getCollection("response");
        Document mostSearched = collection.aggregate(Arrays.asList(group("$Name", Accumulators.sum("total", 1)),
                sort(Sorts.descending("total")))).first();

        mostSearchedDrink = String.valueOf(mostSearched.get("_id"));
        mostSearchedDrinkFrequency = String.valueOf(mostSearched.get("total"));
        mostSearchedDrinkMap.put(mostSearchedDrink,mostSearchedDrinkFrequency);
        return mostSearchedDrinkMap;
    }

    /**
     * This method collects the request information from the user request
     * and enters this data into mongodb.
     * @param jsonRequest
     */
    public void insertRequest(String jsonRequest) {
        MongoCollection<Document> collection = database.getCollection("request");
        collection.insertOne(Document.parse(jsonRequest));
    }

    /**
     * This method collects the response information from the web applciation's response
     * and enters this data into mongodb.
     * @param jsonResponse
     */
    public void insertResponse(String jsonResponse) {
        MongoCollection<Document> collection = database.getCollection("response");
        collection.insertOne(Document.parse(jsonResponse));
    }



}
