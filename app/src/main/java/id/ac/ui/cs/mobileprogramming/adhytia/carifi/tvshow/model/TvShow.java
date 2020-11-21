package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TvShow implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "tv_show_id")
    private int tvShowId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "poster_url")
    private String posterURL;

    @ColumnInfo(name = "backdrop_url")
    private String backdropURL;

    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;

    @ColumnInfo(name = "vote_average")
    private String voteAverage;

    @ColumnInfo(name = "vote_count")
    private String voteCount;

    @ColumnInfo(name = "popularity")
    private String popularity;


    public TvShow() {
    }

    protected TvShow(Parcel in) {
        tvShowId = in.readInt();
        name = in.readString();
        overview = in.readString();
        posterURL = in.readString();
        backdropURL = in.readString();
        firstAirDate = in.readString();
        voteAverage = in.readString();
        voteCount = in.readString();
        popularity = in.readString();
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel in) {
            return new TvShow(in);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(int tvShowId) {
        this.tvShowId = tvShowId;
    }

    public String getBackdropURL() {
        return backdropURL;
    }

    public void setBackdropURL(String backdropURL) {
        this.backdropURL = backdropURL;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String description) {
        this.overview = description;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tvShowId);
        dest.writeString(name);
        dest.writeString(overview);
        dest.writeString(posterURL);
        dest.writeString(backdropURL);
        dest.writeString(firstAirDate);
        dest.writeString(voteAverage);
        dest.writeString(voteCount);
        dest.writeString(popularity);
    }
}
