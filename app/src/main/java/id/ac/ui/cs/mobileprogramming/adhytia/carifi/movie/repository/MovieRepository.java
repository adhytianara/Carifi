package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

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
    public static final String MOVIES_BY_TITLE = "movies_by_title";
    public static final String FETCH_TYPE = "fetch_type";

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
        fetchServiceIntent.putExtra(FETCH_TYPE, 1);
        mActivity.startService(fetchServiceIntent);
    }

    public void searchMovieByTitle(String title) {
        fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Query Selesai", Toast.LENGTH_SHORT).show();
                log.e("DEBUG", "onReceive " + intent.getParcelableArrayListExtra(POPULAR_MOVIES).size());
                ArrayList<Movie> moviesByTitle = intent.getParcelableArrayListExtra(POPULAR_MOVIES);
                movieViewModel.setPopularMovie(moviesByTitle);
            }
        };
        log.e("DEBUG", "fetchPopularMovies, repository");
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH_STATUS);
        mActivity.registerReceiver(fetchReceiver, fetchIntentFilter);

        Intent fetchServiceIntent = new Intent(mActivity, FetchAPIService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 2);
        fetchServiceIntent.putExtra(MOVIES_BY_TITLE, title);
        mActivity.startService(fetchServiceIntent);
    }
}
