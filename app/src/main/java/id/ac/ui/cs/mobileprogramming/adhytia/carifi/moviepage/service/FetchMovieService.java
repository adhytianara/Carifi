package id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.service;

import android.app.IntentService;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.repository.MovieRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FetchAPIService extends IntentService {

    private ArrayList<Movie> movieList = new ArrayList<>();

    public FetchAPIService() {
        super("FetchAPIService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                // Untuk uji lost focus
                Thread.sleep(0);
                fetchBasedOnType(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent notifyFinishIntent = new Intent(MovieRepository.ACTION_FETCH);
            notifyFinishIntent.putParcelableArrayListExtra(MovieRepository.FETCH_RESULT, movieList);
            sendBroadcast(notifyFinishIntent);
        }
    }

    private void fetchBasedOnType(Intent intent) {
        int fetchType = intent.getIntExtra(MovieRepository.FETCH_TYPE, 1);
        switch (fetchType) {
            case 1:
                fetchPopularMovies();
                break;
            case 2:
                String title = intent.getStringExtra(MovieRepository.MOVIES_TITLE);
                fetchMoviesByTitle(title);
                break;
        }
    }

    private void fetchMoviesByTitle(String title) {
        SyncHttpClient client = new SyncHttpClient ();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&query="+title+"&page=1&include_adult=false";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        String noImage = "https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg";

                        int id = Integer.parseInt(movieObject.getString("id"));
                        String title = movieObject.getString("title");
                        String backdropPath = movieObject.getString("backdrop_path");
                        backdropPath = backdropPath.equals("null") ? noImage : baseImageURL + backdropPath;
                        String posterPath = movieObject.getString("poster_path");
                        posterPath = posterPath.equals("null") ? noImage : baseImageURL + posterPath;
                        String overview = movieObject.getString("overview");
                        String releaseDate = movieObject.getString("release_date");
                        String voteAverage = movieObject.getString("vote_average");
                        String voteCount = movieObject.getString("vote_count");
                        String popularity = movieObject.getString("popularity");

                        Movie movie = new Movie();
                        movie.setMovieId(id);
                        movie.setTitle(title);
                        movie.setBackdropURL(backdropPath);
                        movie.setPosterURL(posterPath);
                        movie.setOverview(overview);
                        movie.setReleaseDate(releaseDate);
                        movie.setVoteAverage(voteAverage);
                        movie.setVoteCount(voteCount);
                        movie.setPopularity(popularity);
                        movieList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure from Fetch. Status code: " + statusCode);
            }
        });
    }

    private void fetchPopularMovies() {
        SyncHttpClient client = new SyncHttpClient ();
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";

                        int id = Integer.parseInt(movieObject.getString("id"));
                        String title = movieObject.getString("title");
                        String backdropPath = movieObject.getString("backdrop_path");
                        String posterPath = movieObject.getString("poster_path");
                        String overview = movieObject.getString("overview");
                        String releaseDate = movieObject.getString("release_date");
                        String voteAverage = movieObject.getString("vote_average");
                        String voteCount = movieObject.getString("vote_count");
                        String popularity = movieObject.getString("popularity");

                        Movie movie = new Movie();
                        movie.setMovieId(id);
                        movie.setTitle(title);
                        movie.setBackdropURL(baseImageURL + backdropPath);
                        movie.setPosterURL(baseImageURL + posterPath);
                        movie.setOverview(overview);
                        movie.setReleaseDate(releaseDate);
                        movie.setVoteAverage(voteAverage);
                        movie.setVoteCount(voteCount);
                        movie.setPopularity(popularity);
                        movieList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure from Fetch. Status code: " + statusCode);
            }
        });
    }
}
