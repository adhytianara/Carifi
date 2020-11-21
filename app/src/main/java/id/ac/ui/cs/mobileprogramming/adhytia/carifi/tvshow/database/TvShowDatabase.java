package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;

@Database(entities = {TvShow.class}, exportSchema = false, version = 1)
public abstract class TvShowDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "tvshow";
    public abstract TvShowDao tvShowDao();
}
