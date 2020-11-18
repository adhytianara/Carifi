package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.activitydetails;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATA = "data";
    private Movie data;

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

        data = getIntent().getParcelableExtra(DATA);
        setTitle(data.getTitle());
        ButterKnife.bind(this);
        diplayMovieData(data);

        btnFavorite.setOnClickListener(this);
    }

    private void diplayMovieData(Movie data) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_favorite:

                break;
        }
    }
}