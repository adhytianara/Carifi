package id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.model.User;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE = 1;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    @BindView(R.id.et_Name)
    EditText etName;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.btn_change_language)
    Button btnChangeLanguage;

    @BindView(R.id.tv_change_language)
    TextView tvChangeLanguage;

    private ProfileViewModel profileViewModel;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_profile));

        btnSave.setOnClickListener(this);
        btnChangeLanguage.setOnClickListener(this);
        tvChangeLanguage.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init(this);

        displaySavedData();
    }

    private void displaySavedData() {
        User user = profileViewModel.getuser().getValue();
        String name = user.getName();
        etName.setText(name);

        String encoded = user.getAvatarBase64();
        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
        selectedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        Glide.with(this)
                .load(selectedImage)
                .circleCrop()
                .into(ivAvatar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                profileViewModel.saveUserName(this, etName.getText().toString());

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                profileViewModel.saveAvatar(this, encoded);
                Toast.makeText(this, R.string.profile_data_saved, Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_avatar:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
                break;
            case R.id.btn_change_language:
            case R.id.tv_change_language:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(this)
                        .load(selectedImage)
                        .circleCrop()
                        .into(ivAvatar);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileActivity.this, R.string.something_went_wrong, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(ProfileActivity.this, R.string.picc_image_warning, Toast.LENGTH_LONG).show();
        }
    }
}