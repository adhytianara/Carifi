package id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.viewmodel;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.repository.MovieRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

public class MovieDetailsViewModel extends ViewModel {
    private Movie movie;
    private MovieRepository mRepo;
    private boolean isSaved;

    public void init(Movie data, Activity mActivity) {
        if (movie != null) {
            return;
        }
        movie = data;
        mRepo = MovieRepository.getInstance();
        setSaved(false);
        getMovieById(mActivity);
    }

    private void getMovieById(Activity mActivity) {
        mRepo.getMovieById(movie.getMovieId(), mActivity, this);
    }

    public void saveMovietoDb(Activity mActivity) {
        mRepo.saveMovietoDb(movie, mActivity, this);
    }

    public void deleteMovieByMovieId(Activity mActivity) {
        mRepo.deleteMovieById(movie.getMovieId(), mActivity, this);
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
