package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;
    private MovieRepository mRepo;
    private String currentlyDisplayed;

    public void init(FragmentActivity activity) {
        if (movieList != null) {
            return;
        }
        movieList = new MutableLiveData<>();
        movieList.setValue(new ArrayList<Movie>());
        mRepo = MovieRepository.getInstance(activity, this);
        searchPopularMovies();
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }

    public void setMovieList(List<Movie> movieList){
        this.movieList.postValue(movieList);
    }

    public void searchPopularMovies(){
        currentlyDisplayed = "POPULAR";
        mRepo.searchPopularMovies();
    }

    public void searchMovieByTitle(String title) {
        currentlyDisplayed = "SEARCH_RESULT";
        mRepo.searchMovieByTitle(title);
    }

    public String getCurrentlyDisplayed() {
        return currentlyDisplayed;
    }
}
