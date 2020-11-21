package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.repository;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.favorite.viewmodel.FavoriteItemViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel.TvShowDetailsViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.service.FetchTvShowService;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.viewmodel.TvShowViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.database.TvShowDao;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.database.TvShowDatabase;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;


public class TvShowRepository {
    private static TvShowRepository instance;
    private static TvShowDao tvShowDao;
    public static final String ACTION_FETCH = "action_fetch";
    public static final String FETCH_RESULT = "fetch_result";
    public static final String FETCH_TYPE = "fetch_type";
    public static final String TVSHOW_NAME = "tvshow_name";

    public static TvShowRepository getInstance() {
        if (instance == null) {
            instance = new TvShowRepository();
        }
        return instance;
    }

    public void searchPopularTvShow(FragmentActivity activity, TvShowViewModel tvShowViewModel) {
        registerBroadcastReceiver(activity, tvShowViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchTvShowService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 1);
        activity.startService(fetchServiceIntent);
    }

    public void searchTvShowByName(String name, FragmentActivity activity, TvShowViewModel tvShowViewModel) {
        registerBroadcastReceiver(activity, tvShowViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchTvShowService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 2);
        fetchServiceIntent.putExtra(TVSHOW_NAME, name);
        activity.startService(fetchServiceIntent);
    }

    private void registerBroadcastReceiver(FragmentActivity activity, final TvShowViewModel tvShowViewModel) {
        BroadcastReceiver fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<TvShow> popularTvShow = intent.getParcelableArrayListExtra(FETCH_RESULT);
                tvShowViewModel.setTvShowList(popularTvShow);
            }
        };
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH);
        activity.registerReceiver(fetchReceiver, fetchIntentFilter);
    }

    private void initRoom(Activity activity) {
        tvShowDao = Room.databaseBuilder(activity, TvShowDatabase.class, TvShowDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .tvShowDao();
    }

    public List<TvShow> getAllTvShowFromDb(Activity mActivity, FavoriteItemViewModel favoriteItemViewModel) {
        initRoom(mActivity);
        List<TvShow> tvShowList = tvShowDao.getAll();
        favoriteItemViewModel.setTvShowList(tvShowList);
        return tvShowList;
    }

    public void saveTvShowtoDb(TvShow data, Activity mActivity, TvShowDetailsViewModel tvShowDetailsViewModel) {
        initRoom(mActivity);
        tvShowDao.insert(data);
        tvShowDetailsViewModel.setSaved(true);
    }

    public void getTvShowById(int id, Activity mActivity, TvShowDetailsViewModel tvShowDetailsViewModel) {
        initRoom(mActivity);
        TvShow tvShow = tvShowDao.getByTvShowId(id);

        if (tvShow != null) {
            tvShowDetailsViewModel.setSaved(true);
        } else {
            tvShowDetailsViewModel.setSaved(false);
        }
    }

    public void deleteTvShowById(int id, Activity mActivity, TvShowDetailsViewModel tvShowDetailsViewModel) {
        initRoom(mActivity);
        tvShowDao.deleteByTvShowId(id);
        tvShowDetailsViewModel.setSaved(false);
    }
}
