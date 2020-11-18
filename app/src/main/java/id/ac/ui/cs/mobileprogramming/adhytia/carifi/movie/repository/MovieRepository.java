package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.FetchAPIService;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieViewModel;


public class MovieRepository {
    private BroadcastReceiver fetchReceiver;
    private static MovieRepository instance;
    private static FragmentActivity mActivity;
    private static MovieViewModel movieViewModel;
    public static final String ACTION_FETCH = "action_fetch";
    public static final String FETCH_RESULT = "fetch_result";
    public static final String FETCH_TYPE = "fetch_type";
    public static final String MOVIES_TITLE = "movies_title";

    public static MovieRepository getInstance(FragmentActivity activity, MovieViewModel viewModel) {
        mActivity = activity;
        movieViewModel = viewModel;
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public void searchPopularMovies() {
        registerBroadcastReceiver();

        Intent fetchServiceIntent = new Intent(mActivity, FetchAPIService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 1);
        mActivity.startService(fetchServiceIntent);
    }

    public void searchMovieByTitle(String title) {
        registerBroadcastReceiver();

        Intent fetchServiceIntent = new Intent(mActivity, FetchAPIService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 2);
        fetchServiceIntent.putExtra(MOVIES_TITLE, title);
        mActivity.startService(fetchServiceIntent);
    }

    private void registerBroadcastReceiver() {
        fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Movie> popularMovies = intent.getParcelableArrayListExtra(FETCH_RESULT);
                movieViewModel.setMovieList(popularMovies);
            }
        };
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH);
        mActivity.registerReceiver(fetchReceiver, fetchIntentFilter);
    }
}
