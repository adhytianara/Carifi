package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.service;

import android.app.IntentService;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.R;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.repository.PeopleRepository;

import static com.loopj.android.http.AsyncHttpClient.log;


public class FetchPeopleService extends IntentService {
    private ArrayList<People> peopleList = new ArrayList<>();

    public FetchPeopleService() {
        super("FetchPeopleService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                // Untuk uji lost focus
                Thread.sleep(0);
                fetchBasedOnType(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent notifyFinishIntent = new Intent(PeopleRepository.ACTION_FETCH);
            notifyFinishIntent.putParcelableArrayListExtra(PeopleRepository.FETCH_RESULT, peopleList);
            sendBroadcast(notifyFinishIntent);
        }
    }

    private void fetchBasedOnType(Intent intent) {
        int fetchType = intent.getIntExtra(PeopleRepository.FETCH_TYPE, 1);
        switch (fetchType) {
            case 1:
                fetchPopularPeople();
                break;
            case 2:
                String name = intent.getStringExtra(PeopleRepository.PEOPLE_NAME);
                fetchPeopleByName(name);
                break;
        }
    }

    private void fetchPeopleByName(String name) {
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/search/person?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&query=" + name + "%20&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject peopleObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        String noImage = "https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg";

                        int id = Integer.parseInt(peopleObject.getString("id"));
                        String name = peopleObject.getString("name");
                        String profilePath = peopleObject.getString("profile_path");
                        profilePath = profilePath.equals("null") ? noImage : baseImageURL + profilePath;
                        String gender = peopleObject.getString("gender");
                        gender = gender.equals("1") ? getString(R.string.female) : getString(R.string.male);
                        String department = peopleObject.getString("known_for_department");

                        People people = new People();
                        people.setPeopleId(id);
                        people.setName(name);
                        people.setGender(gender);
                        people.setDepartment(department);
                        people.setProfileURL(profilePath);
                        peopleList.add(people);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure from Fetch. Status code: " + statusCode);
            }
        });
    }

    private void fetchPopularPeople() {
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/person/popular?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject peopleObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        String noImage = "https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg";

                        int id = Integer.parseInt(peopleObject.getString("id"));
                        String name = peopleObject.getString("name");
                        String profilePath = peopleObject.getString("profile_path");
                        profilePath = profilePath.equals("null") ? noImage : baseImageURL + profilePath;
                        String gender = peopleObject.getString("gender");
                        gender = gender.equals("1") ? getString(R.string.female) : getString(R.string.male);
                        String department = peopleObject.getString("known_for_department");

                        People people = new People();
                        people.setPeopleId(id);
                        people.setName(name);
                        people.setGender(gender);
                        people.setDepartment(department);
                        people.setProfileURL(profilePath);
                        peopleList.add(people);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                log.e("DEBUG", "onFailure from Fetch. Status code: " + statusCode);
            }
        });
    }
}
