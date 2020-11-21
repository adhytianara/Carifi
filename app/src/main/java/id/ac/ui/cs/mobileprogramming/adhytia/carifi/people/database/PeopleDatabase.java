package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;

@Database(entities = {People.class}, exportSchema = false, version = 1)
public abstract class PeopleDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "people";

    public abstract PeopleDao peopleDao();
}
