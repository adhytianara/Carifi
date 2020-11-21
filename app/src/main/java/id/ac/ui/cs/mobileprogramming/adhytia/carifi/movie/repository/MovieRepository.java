package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.viewmodel.FavoriteItemViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.database.MovieDao;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.database.MovieDatabase;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.service.FetchMovieService;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieDetailsViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieViewModel;


public class MovieRepository {
    private static MovieRepository instance;
    private static MovieDao movieDao;
    public static final String ACTION_FETCH = "action_fetch";
    public static final String FETCH_RESULT = "fetch_result";
    public static final String FETCH_TYPE = "fetch_type";
    public static final String MOVIES_TITLE = "movies_title";

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    public void searchPopularMovies(FragmentActivity activity, MovieViewModel movieViewModel) {
        registerBroadcastReceiver(activity, movieViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchMovieService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 1);
        activity.startService(fetchServiceIntent);
    }

    public void searchMovieByTitle(String title, FragmentActivity activity, MovieViewModel movieViewModel) {
        registerBroadcastReceiver(activity, movieViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchMovieService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 2);
        fetchServiceIntent.putExtra(MOVIES_TITLE, title);
        activity.startService(fetchServiceIntent);
    }

    private void registerBroadcastReceiver(FragmentActivity activity, final MovieViewModel movieViewModel) {
        BroadcastReceiver fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<Movie> popularMovies = intent.getParcelableArrayListExtra(FETCH_RESULT);
                movieViewModel.setMovieList(popularMovies);
            }
        };
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH);
        activity.registerReceiver(fetchReceiver, fetchIntentFilter);
    }

    private void initRoom(Activity activity) {
        movieDao = Room.databaseBuilder(activity, MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .movieDao();
    }

    public List<Movie> getAllMovieFromDb(Activity mActivity, FavoriteItemViewModel favoriteItemViewModel) {
        initRoom(mActivity);
        List<Movie> movieList = movieDao.getAll();
        favoriteItemViewModel.setMovieList(movieList);
        return movieList;
    }

    public void saveMovietoDb(Movie data, Activity mActivity, MovieDetailsViewModel movieDetailsViewModel) {
        initRoom(mActivity);
        movieDao.insert(data);
        movieDetailsViewModel.setSaved(true);
    }

    public void getMovieById(int id, Activity mActivity, MovieDetailsViewModel movieDetailsViewModel) {
        initRoom(mActivity);
        Movie movie = movieDao.getByMovieId(id);

        if (movie != null) {
            movieDetailsViewModel.setSaved(true);
        } else {
            movieDetailsViewModel.setSaved(false);
        }
    }

    public void deleteMovieById(int id, Activity mActivity, MovieDetailsViewModel movieDetailsViewModel) {
        initRoom(mActivity);
        movieDao.deleteByMovieId(id);
        movieDetailsViewModel.setSaved(false);
    }
}
