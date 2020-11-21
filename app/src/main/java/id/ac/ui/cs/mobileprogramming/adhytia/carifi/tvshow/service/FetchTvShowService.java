package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.service;

import android.app.IntentService;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;
import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.repository.TvShowRepository;

import static com.loopj.android.http.AsyncHttpClient.log;

public class FetchTvShowService extends IntentService {
    private ArrayList<TvShow> tvShowList = new ArrayList<>();

    public FetchTvShowService() {
        super("FetchTvShowService");
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

            Intent notifyFinishIntent = new Intent(TvShowRepository.ACTION_FETCH);
            notifyFinishIntent.putParcelableArrayListExtra(TvShowRepository.FETCH_RESULT, tvShowList);
            sendBroadcast(notifyFinishIntent);
        }
    }

    private void fetchBasedOnType(Intent intent) {
        int fetchType = intent.getIntExtra(TvShowRepository.FETCH_TYPE, 1);
        switch (fetchType) {
            case 1:
                fetchPopularTvShow();
                break;
            case 2:
                String title = intent.getStringExtra(TvShowRepository.TVSHOW_NAME);
                fetchTvShowByTitle(title);
                break;
        }
    }

    private void fetchTvShowByTitle(String tvShowName) {
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&page=1&query=" + tvShowName;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject tvShowObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        String noImage = "https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg";

                        int id = Integer.parseInt(tvShowObject.getString("id"));
                        String name = tvShowObject.getString("name");
                        String backdropPath = tvShowObject.getString("backdrop_path");
                        backdropPath = backdropPath.equals("null") ? noImage : baseImageURL + backdropPath;
                        String posterPath = tvShowObject.getString("poster_path");
                        posterPath = posterPath.equals("null") ? noImage : baseImageURL + posterPath;
                        String overview = tvShowObject.getString("overview");
                        String firstAirDate = tvShowObject.getString("first_air_date");
                        String voteAverage = tvShowObject.getString("vote_average");
                        String voteCount = tvShowObject.getString("vote_count");
                        String popularity = tvShowObject.getString("popularity");

                        TvShow tvShow = new TvShow();
                        tvShow.setTvShowId(id);
                        tvShow.setName(name);
                        tvShow.setBackdropURL(backdropPath);
                        tvShow.setPosterURL(posterPath);
                        tvShow.setOverview(overview);
                        tvShow.setFirstAirDate(firstAirDate);
                        tvShow.setVoteAverage(voteAverage);
                        tvShow.setVoteCount(voteCount);
                        tvShow.setPopularity(popularity);
                        tvShowList.add(tvShow);
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

    private void fetchPopularTvShow() {
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=33802d494b8327ab4203e3260d36ea10&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray resultArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < resultArray.length(); i++) {
                        JSONObject tvShowObject = resultArray.getJSONObject(i);
                        String baseImageURL = "https://image.tmdb.org/t/p/w500";
                        String noImage = "https://upload.wikimedia.org/wikipedia/en/6/60/No_Picture.jpg";

                        int id = Integer.parseInt(tvShowObject.getString("id"));
                        String name = tvShowObject.getString("name");
                        String backdropPath = tvShowObject.getString("backdrop_path");
                        backdropPath = backdropPath.equals("null") ? noImage : baseImageURL + backdropPath;
                        String posterPath = tvShowObject.getString("poster_path");
                        posterPath = posterPath.equals("null") ? noImage : baseImageURL + posterPath;
                        String overview = tvShowObject.getString("overview");
                        String firstAirDate = tvShowObject.getString("first_air_date");
                        String voteAverage = tvShowObject.getString("vote_average");
                        String voteCount = tvShowObject.getString("vote_count");
                        String popularity = tvShowObject.getString("popularity");

                        TvShow tvShow = new TvShow();
                        tvShow.setTvShowId(id);
                        tvShow.setName(name);
                        tvShow.setBackdropURL(backdropPath);
                        tvShow.setPosterURL(posterPath);
                        tvShow.setOverview(overview);
                        tvShow.setFirstAirDate(firstAirDate);
                        tvShow.setVoteAverage(voteAverage);
                        tvShow.setVoteCount(voteCount);
                        tvShow.setPopularity(popularity);
                        tvShowList.add(tvShow);
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
