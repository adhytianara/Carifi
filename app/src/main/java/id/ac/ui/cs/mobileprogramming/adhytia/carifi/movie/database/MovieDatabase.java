package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;

@Database(entities = {Movie.class}, exportSchema = false, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
