package id.ac.ui.cs.mobileprogramming.adhytia.carifi;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FetchAPIService extends IntentService {

    private ArrayList<Movie> popularMovies = new ArrayList<>();

    public FetchAPIService() {
        super("FetchAPIService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                Thread.sleep(0);
                int fetchType = intent.getIntExtra(MovieRepository.FETCH_TYPE, 1);
                switch (fetchType) {
                    case 1:
                        fetchPopularMovies();
                        break;
                    case 2:
                        String title = intent.getStringExtra(MovieRepository.MOVIES_BY_TITLE);
                        fetchMoviesByTitle(title);
                        break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent notifyFinishIntent = new Intent(MovieRepository.ACTION_FETCH_STATUS);
            notifyFinishIntent.putParcelableArrayListExtra(MovieRepository.POPULAR_MOVIES, popularMovies);
            log.e("DEBUG", "notifyFinishIntent");
            sendBroadcast(notifyFinishIntent);
            log.e("DEBUG", "below notifyFinishIntent");
        }
    }

    private void fetchMoviesByTitle(String title) {
        SyncHttpClient client = new SyncHttpClient ();
//        client.addHeader("Authorization", "token 860b13c0e4ab34e0a3c3dad1c21ec7faa4a2ce59");
//        client.addHeader("User-Agent", "request");
//        String url = "https://api.github.com/search/users?q=" + username;
        String url = "https://api.themoviedb.org/3/search/movie?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&query="+title+"&page=1&include_adult=false";
        log.e("DEBUG", "AsyncHttpResponseHandler");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    log.e("DEBUG", "onSuccess before");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        String title = movieObject.getString("title");
                        String posterPath = movieObject.getString("poster_path");
                        Movie movie = new Movie();
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        movie.setPosterURL(baseImageURL + posterPath);
                        movie.setTitle(title);
                        popularMovies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                log.e("DEBUG", "onSuccess");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure");
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
//                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPopularMovies() {
        SyncHttpClient client = new SyncHttpClient ();
//        client.addHeader("Authorization", "token 860b13c0e4ab34e0a3c3dad1c21ec7faa4a2ce59");
//        client.addHeader("User-Agent", "request");
//        String url = "https://api.github.com/search/users?q=" + username;
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&page=1";
        log.e("DEBUG", "AsyncHttpResponseHandler");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    log.e("DEBUG", "onSuccess before");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject movieObject = resultArray.getJSONObject(i);
                        String title = movieObject.getString("title");
                        String posterPath = movieObject.getString("poster_path");
                        Movie movie = new Movie();
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        movie.setPosterURL(baseImageURL + posterPath);
                        movie.setTitle(title);
                        popularMovies.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                log.e("DEBUG", "onSuccess");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure");
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbiden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
//                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
