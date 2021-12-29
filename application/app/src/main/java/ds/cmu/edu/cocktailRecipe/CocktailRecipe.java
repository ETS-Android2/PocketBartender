package ds.cmu.edu.cocktailRecipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ds.cmu.edu.interestingpicture.R;

public class CocktailRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CocktailRecipe cocktailRecipe = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
                System.out.println("searchTerm = " + searchTerm);

                GetRecipe gp = new GetRecipe();
                gp.search(searchTerm, cocktailRecipe);
            }
        });
    }


    public void recipeReady(String recipeJSON) {
        TextView recipeView = findViewById(R.id.recipeDisplay);
        if (recipeJSON != null) {
            recipeView.setText(recipeJSON);
            System.out.println("RECIPE PRINTED");
            recipeView.setVisibility(View.VISIBLE);
        } else {
            System.out.println("No RECIPE");
            recipeView.setVisibility(View.INVISIBLE);
        }
    }
}
