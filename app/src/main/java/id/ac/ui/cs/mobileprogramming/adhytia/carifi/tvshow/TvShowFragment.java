package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;

public class TvShowFragment extends Fragment {

    public TvShowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_tvshow));
        return inflater.inflate(R.layout.fragment_tv_show, container, false);
    }
}