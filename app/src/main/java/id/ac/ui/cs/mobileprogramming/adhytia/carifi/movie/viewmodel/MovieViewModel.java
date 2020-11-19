package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

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
        mRepo = MovieRepository.getInstance();
        mRepo.setMovieViewModel(this);
        searchPopularMovies(activity);
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }

    public void setMovieList(List<Movie> movieList){
        this.movieList.setValue(movieList);
    }

    public void searchPopularMovies(FragmentActivity activity){
        currentlyDisplayed = "POPULAR";
        mRepo.searchPopularMovies(activity);
    }

    public void searchMovieByTitle(String title, FragmentActivity activity) {
        currentlyDisplayed = "SEARCH_RESULT";
        mRepo.searchMovieByTitle(title, activity);
    }

    public String getCurrentlyDisplayed() {
        return currentlyDisplayed;
    }
}
