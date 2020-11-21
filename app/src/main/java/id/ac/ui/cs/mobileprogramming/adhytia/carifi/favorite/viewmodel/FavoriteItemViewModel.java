package id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.viewmodel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.repository.MovieRepository;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.repository.PeopleRepository;

public class FavoriteItemViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;
    private MutableLiveData<List<People>> peopleList;
    private MovieRepository movieRepository;
    private PeopleRepository peopleRepository;

    public void init(Activity mActivity) {
        movieList = new MutableLiveData<>();
        peopleList = new MutableLiveData<>();

        peopleRepository = PeopleRepository.getInstance();
        movieRepository = MovieRepository.getInstance();

        getAllFavoriteItemFromDb(mActivity, this);
    }

    public void getAllFavoriteItemFromDb(Activity mActivity, FavoriteItemViewModel favoriteItemViewModel) {
        List<Movie> movies = movieRepository.getAllMovieFromDb(mActivity, favoriteItemViewModel);
        List<People> people = peopleRepository.getAllPeopleFromDb(mActivity, favoriteItemViewModel);
        movieList.setValue(movies);
        peopleList.setValue(people);
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList.setValue(movieList);
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList.setValue(peopleList);
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<List<People>> getPeopleList() {
        return peopleList;
    }
}
