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
    private MutableLiveData<List<Movie>> popularMovieList;
    private MovieRepository mRepo;

    public void init(FragmentActivity activity) {
        if (popularMovieList != null) {
            return;
        }
        popularMovieList = new MutableLiveData<>();
        popularMovieList.setValue(new ArrayList<Movie>());
        mRepo = MovieRepository.getInstance(activity, this);
        mRepo.getPopularMovies();
    }

    public LiveData<List<Movie>> getPopularMovies(){
        return popularMovieList;
    }

    public void setPopularMovie(List<Movie> popularMovie){
        popularMovieList.postValue(popularMovie);
    }
}
