package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow;

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
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.adapter.TvShowListAdapter;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.tvshowdetails.TvShowDetailsActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel.TvShowViewModel;

public class TvShowFragment extends Fragment {
    @BindView(R.id.rv_tvShow)
    RecyclerView rvTvShow;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.tv_queryResult)
    TextView queryResult;

    private id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel.TvShowViewModel TvShowViewModel;

    public TvShowFragment() {
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
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_tvshow));
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTvShow.setHasFixedSize(true);
        TvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        TvShowViewModel.init(getActivity());
        showRecyclerList();

        TvShowViewModel.getTvShowList().observe(Objects.requireNonNull(getActivity()), new Observer<List<TvShow>>() {
            @Override
            public void onChanged(List<TvShow> TvShow) {
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
                    queryResult.setText(R.string.popular_tvshow);
                    TvShowViewModel.searchPopularTvShow(getActivity());
                } else {
                    String searchResultText = String.format("%s '%s'", getString(R.string.search_result_text), query);
                    queryResult.setText(searchResultText);
                    TvShowViewModel.searchTvShowByName(query, getActivity());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    queryResult.setText(R.string.popular_tvshow);
                    TvShowViewModel.searchPopularTvShow(getActivity());
                }
                return false;
            }
        });
    }

    private void showRecyclerList() {
        TvShowListAdapter TvShowListAdapter = new TvShowListAdapter(TvShowViewModel.getTvShowList().getValue());
        rvTvShow.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvTvShow.setAdapter(TvShowListAdapter);

        TvShowListAdapter.setOnItemClickCallback(new TvShowListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TvShow data) {
                Intent intent = new Intent(getActivity(), TvShowDetailsActivity.class);
                intent.putExtra(TvShowDetailsActivity.DATA, data);
                startActivity(intent);
            }
        });
    }
}