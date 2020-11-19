package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.activitydetails.MovieDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;

public class MovieDetailsViewModel extends ViewModel {
//    private MutableLiveData<Movie> movie;
    private Movie movie;
    private MovieRepository mRepo;
    private boolean isSaved;

    public void init(Movie data, Activity mActivity) {
        if (movie != null) {
            return;
        }
//        movie = new MutableLiveData<>();
//        movie.setValue(data);
        movie = data;
        mRepo = MovieRepository.getInstance();
        mRepo.setMovieDetailsViewModel(this);
        setSaved(false);
        getMovieById(mActivity);
    }

    private void getMovieById(Activity mActivity) {
        mRepo.getMovieById(movie.get_id(), mActivity);
    }

//    public void init(FragmentActivity activity) {
//        if (movieList != null) {
//            return;
//        }
//        movieList = new MutableLiveData<>();
//        movieList.setValue(new ArrayList<Movie>());
//        mRepo = MovieRepository.getInstance();
//        mRepo.setMovieDetailsViewModel(this);
//        getAllMovieFromDb(activity);
//    }

//    private void getAllMovieFromDb(Activity activity) {
//        List<Movie> movies = mRepo.getAllMovieFromDb(activity);
//        movieList.setValue(movies);
//    }

    public void saveMovietoDb(Activity mActivity) {
        mRepo.saveMovietoDb(movie, mActivity);
    }

    public void deleteMovieById(Activity mActivity) {
        mRepo.deleteMovieById(movie, mActivity);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
