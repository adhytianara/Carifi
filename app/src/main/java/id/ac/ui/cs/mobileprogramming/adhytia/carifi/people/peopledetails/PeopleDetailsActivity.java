package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.peopledetails;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.FavoriteActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel.PeopleDetailsViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.ProfileActivity;

public class PeopleDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String DATA = "data";
    private PeopleDetailsViewModel peopleDetailsViewModel;

    @BindView(R.id.img_picture)
    ImageView imgPicture;

    @BindView(R.id.img_picture2)
    ImageView imgPicture2;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_gender)
    TextView tvGender;

    @BindView(R.id.tv_department)
    TextView tvDepartment;

    @BindView(R.id.btn_favorite)
    Button btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);

        People data = getIntent().getParcelableExtra(DATA);
        setTitle(data.getName());
        ButterKnife.bind(this);

        peopleDetailsViewModel = ViewModelProviders.of(this).get(PeopleDetailsViewModel.class);
        peopleDetailsViewModel.init(data, this);
        btnFavorite.setOnClickListener(this);

        diplayPeopleData(data);
    }

    private void diplayPeopleData(People data) {
        boolean peopleIsSaved = peopleDetailsViewModel.isSaved();
        btnFavorite.setBackgroundResource(peopleIsSaved ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        Glide.with(this)
                .load(data.getProfileURL())
                .apply(new RequestOptions().override(200, 200))
                .into(imgPicture);
        Glide.with(this)
                .load(data.getProfileURL())
                .apply(new RequestOptions().override(500, 500))
                .into(imgPicture2);

        tvName.setText(data.getName());
        tvGender.setText(String.format("%s%s", getString(R.string.gender), data.getGender()));
        tvDepartment.setText(String.format("%s%s", getString(R.string.department), data.getDepartment()));
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
                Intent favorite = new Intent(PeopleDetailsActivity.this, FavoriteActivity.class);
                startActivity(favorite);
                break;
            case R.id.menu_profile:
                Intent profile = new Intent(PeopleDetailsActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_favorite:
                if (peopleDetailsViewModel.isSaved()) {
                    peopleDetailsViewModel.deletePeopleByPeopleId(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border);
                } else {
                    peopleDetailsViewModel.savePeopletoDb(this);
                    btnFavorite.setBackgroundResource(R.drawable.ic_favorite);
                }
                break;
        }
    }
}