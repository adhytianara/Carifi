package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.repository;

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
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.database.PeopleDao;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.database.PeopleDatabase;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.service.FetchPeopleService;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel.PeopleDetailsViewModel;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.viewmodel.PeopleViewModel;


public class PeopleRepository {
    private static PeopleRepository instance;
    private static PeopleDao peopleDao;
    public static final String ACTION_FETCH = "action_fetch";
    public static final String FETCH_RESULT = "fetch_result";
    public static final String FETCH_TYPE = "fetch_type";
    public static final String PEOPLE_NAME = "people_name";

    public static PeopleRepository getInstance() {
        if (instance == null) {
            instance = new PeopleRepository();
        }
        return instance;
    }

    public void searchPopularPeople(FragmentActivity activity, PeopleViewModel peopleViewModel) {
        registerBroadcastReceiver(activity, peopleViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchPeopleService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 1);
        activity.startService(fetchServiceIntent);
    }

    public void searchPeopleByName(String name, FragmentActivity activity, PeopleViewModel peopleViewModel) {
        registerBroadcastReceiver(activity, peopleViewModel);

        Intent fetchServiceIntent = new Intent(activity, FetchPeopleService.class);
        fetchServiceIntent.putExtra(FETCH_TYPE, 2);
        fetchServiceIntent.putExtra(PEOPLE_NAME, name);
        activity.startService(fetchServiceIntent);
    }

    private void registerBroadcastReceiver(FragmentActivity activity, final PeopleViewModel peopleViewModel) {
        BroadcastReceiver fetchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<People> popularPeople = intent.getParcelableArrayListExtra(FETCH_RESULT);
                peopleViewModel.setPeopleList(popularPeople);
            }
        };
        IntentFilter fetchIntentFilter = new IntentFilter(ACTION_FETCH);
        activity.registerReceiver(fetchReceiver, fetchIntentFilter);
    }

    private void initRoom(Activity activity) {
        peopleDao = Room.databaseBuilder(activity, PeopleDatabase.class, PeopleDatabase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .peopleDao();
    }

    public List<People> getAllPeopleFromDb(Activity mActivity, FavoriteItemViewModel favoriteItemViewModel) {
        initRoom(mActivity);
        List<People> peopleList = peopleDao.getAll();
        favoriteItemViewModel.setPeopleList(peopleList);
        return peopleList;
    }

    public void savePeopletoDb(People data, Activity mActivity, PeopleDetailsViewModel peopleDetailsViewModel) {
        initRoom(mActivity);
        peopleDao.insert(data);
        peopleDetailsViewModel.setSaved(true);
    }

    public void getPeopleById(int id, Activity mActivity, PeopleDetailsViewModel peopleDetailsViewModel) {
        initRoom(mActivity);
        People people = peopleDao.getByPeopleId(id);

        if (people != null) {
            peopleDetailsViewModel.setSaved(true);
        } else {
            peopleDetailsViewModel.setSaved(false);
        }
    }

    public void deletePeopleById(int id, Activity mActivity, PeopleDetailsViewModel peopleDetailsViewModel) {
        initRoom(mActivity);
        peopleDao.deleteByPeopleId(id);
        peopleDetailsViewModel.setSaved(false);
    }
}
