# PocketBartender

An android app that provides the recipe and other relevant information for any cocktail drink!

## Application

Leveraging the [CocktailDB API](https://www.thecocktaildb.com/api.php), the Pocket Bartender application calls a web servlet written on Apache TomEE. The servlet then hits the CocktailDB api and returns the processed response back to the andoid application so the user can view it.

The web servlet is implemented as a docker container and has been deployed on Heroku.

## Dashboard

A seperate dashboard has been built to keep track of user logs and analytics. The Logs Dashboard keep track of the user information like the web browser used by user to retrieve information. The Analytics Dashboard performs simple analytics on the holistic user logs and provides this data.

The logs are maintained in a MongoDB Atlas Database, which is updated each time a user finds a cocktail recipe on the app. 

## Demo

[PocketBartender Demo Video 1](https://www.youtube.com/watch?v=E-n0fOiNluU) <br />
[PocketBartender Demo Video 2](https://www.youtube.com/watch?v=5jQaZ7yFnx)

