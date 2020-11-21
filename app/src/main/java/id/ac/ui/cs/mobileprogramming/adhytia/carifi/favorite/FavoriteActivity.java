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
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.viewmodel.FavoriteMovieViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.moviedetails.MovieDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;

public class FavoriteActivity extends AppCompatActivity {
    @BindView(R.id.rv_favorite_movies)
    RecyclerView rvFavoriteMovies;

    @BindView(R.id.tv_favorite_movie)
    TextView tvFavoriteMovie;

    @BindView(R.id.tv_have_favorite)
    TextView tvHaveFavorite;

    private FavoriteMovieViewModel favoriteMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(getString(R.string.title_favorite));
        ButterKnife.bind(this);

        rvFavoriteMovies.setHasFixedSize(true);
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        favoriteMovieViewModel.init(this);
        showRecyclerList();

        favoriteMovieViewModel.getMovieList().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                showRecyclerList();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favoriteMovieViewModel.getAllMovieFromDb(this, favoriteMovieViewModel);
    }

    private void showRecyclerList() {
        List<Movie> movieList = favoriteMovieViewModel.getMovieList().getValue();
        tvFavoriteMovie.setVisibility(movieList.isEmpty() ? View.GONE : View.VISIBLE);
        rvFavoriteMovies.setVisibility(movieList.isEmpty() ? View.GONE : View.VISIBLE);
        tvHaveFavorite.setVisibility(movieList.isEmpty() ? View.VISIBLE : View.GONE);

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
}