package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.MainActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;

import static com.loopj.android.http.AsyncHttpClient.log;


public class MovieRepository {
    private static MovieRepository instance;
    private ArrayList<Movie> popularMovies = new ArrayList<>();

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Movie>> getPopularMovies(){
        setPopularMovies();
        MutableLiveData<List<Movie>> dataSet = new MutableLiveData<>();
        dataSet.setValue(popularMovies);
        log.w("DEBUG3", String.valueOf(dataSet.getValue().size()));
        return dataSet;
    }

    private void setPopularMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
//        client.addHeader("Authorization", "token 860b13c0e4ab34e0a3c3dad1c21ec7faa4a2ce59");
//        client.addHeader("User-Agent", "request");
//        String url = "https://api.github.com/search/users?q=" + username;
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
                        String title = movieObject.getString("title");
                        String posterPath = movieObject.getString("poster_path");
                        Movie movie = new Movie();
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        movie.setPosterURL(baseImageURL+posterPath);
                        movie.setTitle(title);
                        popularMovies.add(movie);
                        log.w("DEBUG5", String.valueOf(movie.getPosterURL()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                log.w("DEBUG4", String.valueOf(popularMovies.size()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
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
