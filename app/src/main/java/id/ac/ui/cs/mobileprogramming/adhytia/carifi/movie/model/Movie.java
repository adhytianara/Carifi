package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String title, detail, posterURL;

    protected Movie(Parcel in) {
        title = in.readString();
        detail = in.readString();
        posterURL = in.readString();
    }

    public Movie() {
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String description) {
        this.detail = description;
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
        dest.writeString(title);
        dest.writeString(detail);
        dest.writeString(posterURL);
    }
}
