package id.ac.ui.cs.mobileprogramming.adhytia.carifi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.FavoriteActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.MovieFragment;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.PeopleFragment;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.profile.ProfileActivity;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.TvShowFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_01";
    private static final CharSequence CHANNEL_NAME = "carifi channel";
    private static boolean notifAlreadyCreated = false;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNav;

    @BindView(R.id.layout_connectivity)
    LinearLayout layoutConnectivity;

    @BindView(R.id.btn_connectivity)
    Button btnConnectivity;

    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        btnConnectivity.setOnClickListener(this);

        if (isConnected()) {
            diplayContent();
        }
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
                Intent favorite = new Intent(HomeActivity.this, FavoriteActivity.class);
                startActivity(favorite);
                break;
            case R.id.menu_profile:
                Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (isConnected()) {
                        frameLayout.setVisibility(View.VISIBLE);
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
                    } else {
                        frameLayout.setVisibility(View.GONE);
                    }
                    return false;
                }
            };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_connectivity:
                if (isConnected()) {
                    diplayContent();
                    frameLayout.setVisibility(View.VISIBLE);
                } else {
                    frameLayout.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void diplayContent() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MovieFragment())
                .commit();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            layoutConnectivity.setVisibility(View.GONE);
        } else {
            layoutConnectivity.setVisibility(View.VISIBLE);
        }
        return isConnected;
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!notifAlreadyCreated) {
            notifAlreadyCreated = true;
            countdownNotif();
        }
    }

    private void countdownNotif() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    sendNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendNotification() {
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_favorite)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_carifi))
                .setContentTitle(getString(R.string.notif_content_title))
                .setContentText(getString(R.string.notif_content_text))
                .setAutoCancel(true);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_NAME.toString());

            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}