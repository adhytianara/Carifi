package id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.adapter.FavoriteMovieAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.adapter.FavoritePeopleAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.viewmodel.FavoriteItemViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.moviedetails.MovieDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.peopledetails.PeopleDetailsActivity;

public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.rv_favorite_movies)
    RecyclerView rvFavoriteMovies;

    @BindView(R.id.tv_favorite_movie)
    TextView tvFavoriteMovie;

    @BindView(R.id.tv_have_favorite)
    TextView tvHaveFavorite;

    @BindView(R.id.rv_favorite_tvshow)
    RecyclerView rvFavoriteTvShow;

    @BindView(R.id.tv_favorite_tvshow)
    TextView tvFavoriteTvShow;

    @BindView(R.id.rv_favorite_people)
    RecyclerView rvFavoritePeople;

    @BindView(R.id.tv_favorite_people)
    TextView tvFavoritePeople;

    private List<Movie> movieList;
    private List<People> peopleList;

    private FavoriteItemViewModel favoriteItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(getString(R.string.title_favorite));
        ButterKnife.bind(this);

        rvFavoriteMovies.setHasFixedSize(true);
        rvFavoritePeople.setHasFixedSize(true);
        favoriteItemViewModel = ViewModelProviders.of(this).get(FavoriteItemViewModel.class);
        favoriteItemViewModel.init(this);
        showMovieList();
        showPeopleList();

        favoriteItemViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                showMovieList();
            }
        });
        favoriteItemViewModel.getPeopleList().observe(this, new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
                showPeopleList();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favoriteItemViewModel.getAllFavoriteItemFromDb(this, favoriteItemViewModel);
    }

    private void showMovieList() {
        movieList = favoriteItemViewModel.getMovieList().getValue();
        tvFavoriteMovie.setVisibility(movieList.isEmpty() ? View.GONE : View.VISIBLE);
        rvFavoriteMovies.setVisibility(movieList.isEmpty() ? View.GONE : View.VISIBLE);
        tvHaveFavorite.setVisibility(movieList.isEmpty() && peopleList.isEmpty() ? View.VISIBLE : View.GONE);

        FavoriteMovieAdapter movieListAdapter = new FavoriteMovieAdapter(movieList);
        rvFavoriteMovies.setLayoutManager(new LinearLayoutManager(this));
        rvFavoriteMovies.setAdapter(movieListAdapter);

        movieListAdapter.setOnItemClickCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(FavoriteActivity.this, MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.DATA, data);
                startActivity(intent);
            }
        });
    }

    private void showPeopleList() {
        peopleList = favoriteItemViewModel.getPeopleList().getValue();
        tvFavoritePeople.setVisibility(peopleList.isEmpty() ? View.GONE : View.VISIBLE);
        rvFavoritePeople.setVisibility(peopleList.isEmpty() ? View.GONE : View.VISIBLE);
        tvHaveFavorite.setVisibility(movieList.isEmpty() && peopleList.isEmpty() ? View.VISIBLE : View.GONE);

        FavoritePeopleAdapter peopleListAdapter = new FavoritePeopleAdapter(peopleList);
        rvFavoritePeople.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritePeople.setAdapter(peopleListAdapter);

        peopleListAdapter.setOnItemClickCallback(new FavoritePeopleAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(People data) {
                Intent intent = new Intent(FavoriteActivity.this, PeopleDetailsActivity.class);
                intent.putExtra(PeopleDetailsActivity.DATA, data);
                startActivity(intent);
            }
        });
    }
}