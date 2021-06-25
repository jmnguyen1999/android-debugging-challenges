package com.codepath.debuggingchallenges.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    private String title;
    private String posterUrl;
    private double rating;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterUrl = jsonObject.getString("poster_path");

        //Step 4.) From running a sample network request: The key: "original-title" doesn't exist -> change it to correct title: "original_title"
        //Old:  this.title = jsonObject.getString("original-title");
        //Revised:
        this.title = jsonObject.getString("original_title");

        this.rating = jsonObject.getDouble("vote_average");

        //Step 5.) If you've followed all steps, Logcat now shows the movies List being created, BUT we still cannot see the movies -> Go back to MainActivity AsyncHttpClient's  onSuccess().
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getPosterUrl() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterUrl);
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Movie> results = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                results.add(new Movie(jsonArray.getJSONObject(i)));
                Log.d("Test", "movie #" + i +"  = " + results.get(i));

                //Step 3.)  Add Log above + run to see if movies are being created -> never runs, checkout the Movie constructor
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("Test", "results = " + results.toString());
        return results;
    }
}
