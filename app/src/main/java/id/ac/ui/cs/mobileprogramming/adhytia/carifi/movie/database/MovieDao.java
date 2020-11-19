package id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.movie.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY _id DESC")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE _id = :uid")
    Movie getById(int uid);

    @Insert
    void insertAll(Movie... movies);

    @Insert
    long insert(Movie movie);

    @Query("DELETE FROM movie WHERE _id = :uid")
    int deleteById(long uid);
}
