package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;

public class PeopleFragment extends Fragment {

    public PeopleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(getString(R.string.title_people));
        return inflater.inflate(R.layout.fragment_people, container, false);
    }
}