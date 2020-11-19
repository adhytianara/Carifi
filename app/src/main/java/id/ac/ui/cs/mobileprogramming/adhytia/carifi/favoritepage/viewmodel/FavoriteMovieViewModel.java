package id.ac.ui.cs.mobileprogramming.adhytia.carifi.favoritepage.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.repository.MovieRepository;

public class FavoriteMovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;
    private MovieRepository mRepo;

    public void init(Activity mActivity) {
        if (movieList != null) {
            return;
        }
        movieList = new MutableLiveData<>();
        movieList.setValue(new ArrayList<Movie>());
        mRepo = MovieRepository.getInstance();
        getAllMovieFromDb(mActivity, this);
    }

    public void getAllMovieFromDb(Activity mActivity, FavoriteMovieViewModel favoriteMovieViewModel) {
        List<Movie> movies = mRepo.getAllMovieFromDb(mActivity, favoriteMovieViewModel);
        movieList.setValue(movies);
    }

    public void setMovieList(List<Movie> movieList){
        this.movieList.setValue(movieList);
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }
}
