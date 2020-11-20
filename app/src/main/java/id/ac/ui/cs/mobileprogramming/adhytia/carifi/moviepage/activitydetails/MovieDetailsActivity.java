package id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.activitydetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.HomeActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favoritepage.FavoriteActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.viewmodel.MovieDetailsViewModel;
import okhttp3.OkHttpClient;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATA = "data";
    private Movie data;
    private MovieDetailsViewModel movieDetailsViewModel;

    @BindView(R.id.img_backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.img_poster)
    ImageView imgPoster;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;

    @BindView(R.id.tv_popularity)
    TextView tvPopularity;

    @BindView(R.id.tv_vote_avg)
    TextView tvVoteAvg;

    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;

    @BindView(R.id.btn_favorite)
    Button btnFavorite;

    @BindView(R.id.btn_share)
    Button btnShare;

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        initStetho();

        data = getIntent().getParcelableExtra(DATA);
        setTitle(data.getTitle());
        ButterKnife.bind(this);

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieDetailsViewModel.init(data, this);
        btnFavorite.setOnClickListener(this);

        diplayMovieData(data);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    private void diplayMovieData(Movie data) {
        boolean movieIsSaved = movieDetailsViewModel.isSaved();
        btnFavorite.setBackgroundResource(movieIsSaved ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        Glide.with(this)
                .load(data.getPosterURL())
                .apply(new RequestOptions().override(200, 200))
                .into(imgPoster);
        Glide.with(this)
                .load(data.getBackdropURL())
                .apply(new RequestOptions().override(500, 500))
                .into(imgBackdrop);

        tvTitle.setText(data.getTitle());
        tvReleaseDate.setText("Release date: " + data.getReleaseDate());
        tvPopularity.setText("Popularity: " + data.getPopularity());
        tvVoteAvg.setText("Vote average: " + data.getVoteAverage());
        tvVoteCount.setText("Vote count: " + data.getVoteCount());
        tvOverview.setText(data.getOverview());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appbar_options, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                Intent favorite = new Intent(MovieDetailsActivity.this, FavoriteActivity.class);
                startActivity(favorite);
                break;
            case R.id.menu_settings:
//                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(mIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_favorite:
                if (movieDetailsViewModel.isSaved()) {
//                    movieDetailsViewModel.setSaved(false);
                    movieDetailsViewModel.deleteMovieByMovieId(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border);
                } else {
//                    movieDetailsViewModel.setSaved(true);
                    movieDetailsViewModel.saveMovietoDb(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite);
                }
                break;
        }
    }
}