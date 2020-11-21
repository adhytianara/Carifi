package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.adapter.MovieListAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.moviedetails.MovieDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieViewModel;

public class MovieFragment extends Fragment {
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.tv_queryResult)
    TextView queryResult;

    private MovieViewModel movieViewModel;

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.clearFocus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_movie));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies.setHasFixedSize(true);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init(getActivity());
        showRecyclerList();

        movieViewModel.getMovieList().observe(Objects.requireNonNull(getActivity()), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                showRecyclerList();
            }
        });

        searchViewListener();
    }

    private void searchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    queryResult.setText(R.string.popular_movies);
                    movieViewModel.searchPopularMovies(getActivity());
                } else {
                    String searchResultText = String.format("%s '%s'", getString(R.string.search_result_text), query);
                    queryResult.setText(searchResultText);
                    movieViewModel.searchMovieByTitle(query, getActivity());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    queryResult.setText(R.string.popular_movies);
                    movieViewModel.searchPopularMovies(getActivity());
                }
                return false;
            }
        });
    }

    private void showRecyclerList() {
        MovieListAdapter movieListAdapter = new MovieListAdapter(movieViewModel.getMovieList().getValue());
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMovies.setAdapter(movieListAdapter);

        movieListAdapter.setOnItemClickCallback(new MovieListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
                Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                intent.putExtra(MovieDetailsActivity.DATA, data);
                startActivity(intent);
            }
        });
    }
}