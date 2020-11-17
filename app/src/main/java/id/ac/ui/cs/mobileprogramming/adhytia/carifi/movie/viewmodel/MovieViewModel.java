package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> popularMovieList;
    private MovieRepository mRepo;

    public void init() {
        if (popularMovieList != null) {
            return;
        }
        mRepo = MovieRepository.getInstance();
        popularMovieList = mRepo.getPopularMovies();
        log.w("DEBUG1", String.valueOf(mRepo.getPopularMovies().getValue().size()));
    }

    public LiveData<List<Movie>> getPopularMovies(){
        return popularMovieList;
    }
}
