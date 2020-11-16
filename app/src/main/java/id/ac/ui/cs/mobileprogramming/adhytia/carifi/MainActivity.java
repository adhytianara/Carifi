package id.ac.ui.cs.mobileprogramming.adhytia.carifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNav;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_movie:
                            selectedFragment = new MovieFragment();
                            break;
                        case R.id.nav_tvShow:
                            selectedFragment = new TvShowFragment();
                            break;
                        case R.id.nav_people:
                            selectedFragment = new PeopleFragment();
                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MovieFragment())
                .commit();
    }
}