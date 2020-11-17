package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.FetchAPIService;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieViewModel;

import static com.loopj.android.http.AsyncHttpClient.log;


public class MovieRepository {
    private BroadcastReceiver fetchReceiver;
    private static MovieRepository instance;
    private static FragmentActivity mActivity;
    private static MovieViewModel movieViewModel;
    public static final String ACTION_FETCH_STATUS = "fetch_status";
    public static final String POPULAR_MOVIES = "popular_movies";

    public static MovieRepository getInstance(FragmentActivity activity, MovieViewModel viewModel) {
        mActivity = activity;
        movieViewModel = viewModel;
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public void getPopularMovies() {
        fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show();
                log.e("DEBUG", "onReceive " + intent.getParcelableArrayListExtra(POPULAR_MOVIES).size());
                ArrayList<Movie> popularMovies = intent.getParcelableArrayListExtra(POPULAR_MOVIES);
                movieViewModel.setPopularMovie(popularMovies);
            }
        };
        log.e("DEBUG", "fetchPopularMovies, repository");
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH_STATUS);
        mActivity.registerReceiver(fetchReceiver, fetchIntentFilter);

        Intent fetchServiceIntent = new Intent(mActivity, FetchAPIService.class);
        mActivity.startService(fetchServiceIntent);
    }
}
