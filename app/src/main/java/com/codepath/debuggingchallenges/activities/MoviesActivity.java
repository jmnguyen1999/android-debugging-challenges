package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    RecyclerView rvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rvMovies = findViewById(R.id.rvMovies);

        //Step 14.) Notice we never initialized our movies List! Initialize it to an empty List:
        movies = new ArrayList<>();


        // Step 8:  Notice we declared and initialized a LOCAL adapter, NOT the global one we declared on line 27! Initialize the global adapter variable instead so fetchMovies() can use it!
        //Old:  MoviesAdapter adapter = new MoviesAdapter(movies);
        //Revised:
        adapter = new MoviesAdapter(movies);

        rvMovies.setAdapter(adapter);


        // Step 9:  Notice we have set the RecyclerView's adapter, but not the LayoutManager! Set a LinearLayoutManager for rvMovies!
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        //Step 10:  At this point, app still doesn't show movies. We know: (1) Network requesting works,  (2) Making our list of movies works,  (3) Our Adapter is notified,  (4) Our Recycler View is set up.
        //  Where else have we not looked at? -> MovieAdapter and xml files.  Let's checkout MovieAdapter to see if its binding it's movies correctly!

        fetchMovies();
    }


    private void fetchMovies() {
        // Step 1.) There's an extra space in the url AND there is no API key! Delete space and add the API key:
        //Old: String url = " https://api.themoviedb.org/3/movie/now_playing?api_key=";
        //Revised:
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY;

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON response) {
                try {
                    Log.d("Test", "success getting a json response!");
                    JSONArray moviesJson = response.jsonObject.getJSONArray("results");

                    // Step 6.) Notice in line 69 we are saying a List = List, in Java this will NOT save all the movie items -> use movies.addAll() instead!
                    //Old: movies = (Movie.fromJSONArray(moviesJson));
                    //Revised:
                    movies.addAll(Movie.fromJSONArray(moviesJson));
                    Log.d("Test", "movies = " + movies.toString());
                    // Step 2.) Added Log statements on lines 67 and 70 to test successful request and movies List! Movies was empty -> checkout Movie.java's fromJSONArray()!

                    // Step 7.) movies List is now populated, but adapter doesn't know about the change -> notify it!
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(MoviesActivity.class.getSimpleName(), "Error retrieving movies: ", throwable);
            }
        });
    }
}
