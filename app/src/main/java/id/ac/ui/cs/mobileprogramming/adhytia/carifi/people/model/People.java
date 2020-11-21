package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class People implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int _id;

    @ColumnInfo(name = "people_id")
    private int peopleId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "profile_url")
    private String profileURL;

    @ColumnInfo(name = "known_for")
    private String department;

    protected People(Parcel in) {
        _id = in.readInt();
        peopleId = in.readInt();
        name = in.readString();
        gender = in.readString();
        profileURL = in.readString();
        department = in.readString();
    }

    public People() {

    }

    public static final Creator<People> CREATOR = new Creator<People>() {
        @Override
        public People createFromParcel(Parcel in) {
            return new People(in);
        }

        @Override
        public People[] newArray(int size) {
            return new People[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(peopleId);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(profileURL);
        dest.writeString(department);
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
