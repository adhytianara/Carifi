package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.MainActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.adapter.MovieListAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.viewmodel.MovieViewModel;

import static com.loopj.android.http.AsyncHttpClient.log;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {
    @BindView(R.id.rv_movies)
    private RecyclerView rvMovies;

    private MovieViewModel movieViewModel;
    private MovieListAdapter movieListAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1, String param2) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle("Movies");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies.setHasFixedSize(true);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.init();
        showRecyclerList();

        movieViewModel.getPopularMovies().observe(getActivity(), new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showRecyclerList() {
        log.w("DEBUG", String.valueOf(movieViewModel.getPopularMovies().getValue().size()));
        movieListAdapter = new MovieListAdapter(movieViewModel.getPopularMovies().getValue());
//        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvMovies.setAdapter(movieListAdapter);

        movieListAdapter.setOnItemClickCallback(new MovieListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie data) {
//                listDestinationViewModel.moveToSelectedDestinationDetails(data, getFragmentManager());
                Toast.makeText(getActivity(), data.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}