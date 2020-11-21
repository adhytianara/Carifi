package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.tvshowdetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.FavoriteActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.ProfileActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel.TvShowDetailsViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;

public class TvShowDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATA = "data";
    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @BindView(R.id.img_backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.img_poster)
    ImageView imgPoster;

    @BindView(R.id.tv_name)
    TextView tvName;

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

    @BindView(R.id.tv_overview)
    TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_details);

        TvShow data = getIntent().getParcelableExtra(DATA);
        setTitle(data.getName());
        ButterKnife.bind(this);

        tvShowDetailsViewModel = ViewModelProviders.of(this).get(TvShowDetailsViewModel.class);
        tvShowDetailsViewModel.init(data, this);
        btnFavorite.setOnClickListener(this);

        diplayTvShowData(data);
    }

    private void diplayTvShowData(TvShow data) {
        boolean tvShowIsSaved = tvShowDetailsViewModel.isSaved();
        btnFavorite.setBackgroundResource(tvShowIsSaved ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        Glide.with(this)
                .load(data.getPosterURL())
                .apply(new RequestOptions().override(200, 200))
                .into(imgPoster);
        Glide.with(this)
                .load(data.getBackdropURL())
                .apply(new RequestOptions().override(500, 500))
                .into(imgBackdrop);

        tvName.setText(data.getName());
        tvReleaseDate.setText(String.format("%s%s", getString(R.string.release_date), data.getFirstAirDate()));
        tvPopularity.setText(String.format("%s%s", getString(R.string.popularity), data.getPopularity()));
        tvVoteAvg.setText(String.format("%s%s", getString(R.string.vote_average), data.getVoteAverage()));
        tvVoteCount.setText(String.format("%s%s", getString(R.string.vote_count), data.getVoteCount()));
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
                Intent favorite = new Intent(TvShowDetailsActivity.this, FavoriteActivity.class);
                startActivity(favorite);
                break;
            case R.id.menu_profile:
                Intent profile = new Intent(TvShowDetailsActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_favorite:
                if (tvShowDetailsViewModel.isSaved()) {
                    tvShowDetailsViewModel.deleteTvShowByTvShowId(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border);
                } else {
                    tvShowDetailsViewModel.saveTvShowtoDb(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite);
                }
                break;
        }
    }
}