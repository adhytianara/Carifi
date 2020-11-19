package id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.moviepage.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie ORDER BY _id DESC")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    Movie getByMovieId(int movieId);

    @Insert
    void insertAll(Movie... movies);

    @Insert
    long insert(Movie movie);

    @Query("DELETE FROM movie WHERE movie_id = :movieId")
    int deleteByMovieId(long movieId);
}
