package id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.viewmodel;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.repository.MovieRepository;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;
    private MovieRepository mRepo;

    public void init(FragmentActivity activity) {
        if (movieList != null) {
            return;
        }
        movieList = new MutableLiveData<>();
        movieList.setValue(new ArrayList<Movie>());
        mRepo = MovieRepository.getInstance();
        searchPopularMovies(activity);
    }

    public LiveData<List<Movie>> getMovieList(){
        return movieList;
    }

    public void setMovieList(List<Movie> movieList){
        this.movieList.setValue(movieList);
    }

    public void searchPopularMovies(FragmentActivity activity){
        mRepo.searchPopularMovies(activity, this);
    }

    public void searchMovieByTitle(String title, FragmentActivity activity) {
        mRepo.searchMovieByTitle(title, activity, this);
    }
}
