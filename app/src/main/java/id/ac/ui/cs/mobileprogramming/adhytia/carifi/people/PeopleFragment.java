package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people;

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
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.adapter.PeopleListAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.peopledetails.PeopleDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel.PeopleViewModel;

public class PeopleFragment extends Fragment {
    @BindView(R.id.rv_people)
    RecyclerView rvPeople;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.tv_queryResult)
    TextView queryResult;

    private PeopleViewModel peopleViewModel;


    public PeopleFragment() {
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
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_people));
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPeople.setHasFixedSize(true);
        peopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel.class);
        peopleViewModel.init(getActivity());
        showRecyclerList();

        peopleViewModel.getPeopleList().observe(Objects.requireNonNull(getActivity()), new Observer<List<People>>() {
            @Override
            public void onChanged(List<People> people) {
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
                    queryResult.setText(R.string.popular_people);
                    peopleViewModel.searchPopularPeople(getActivity());
                } else {
                    String searchResultText = String.format("%s '%s'", getString(R.string.search_result_text), query);
                    queryResult.setText(searchResultText);
                    peopleViewModel.searchPeopleByName(query, getActivity());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    queryResult.setText(R.string.popular_people);
                    peopleViewModel.searchPopularPeople(getActivity());
                }
                return false;
            }
        });
    }

    private void showRecyclerList() {
        PeopleListAdapter peopleListAdapter = new PeopleListAdapter(peopleViewModel.getPeopleList().getValue());
        rvPeople.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvPeople.setAdapter(peopleListAdapter);

        peopleListAdapter.setOnItemClickCallback(new PeopleListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(People data) {
                Intent intent = new Intent(getActivity(), PeopleDetailsActivity.class);
                intent.putExtra(PeopleDetailsActivity.DATA, data);
                startActivity(intent);
            }
        });
    }
}