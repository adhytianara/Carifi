package id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.tvshow.model.TvShow;

@Dao
public interface TvShowDao {
    @Query("SELECT * FROM tvshow ORDER BY _id DESC")
    List<TvShow> getAll();

    @Query("SELECT * FROM tvshow WHERE tv_show_id = :tvShowId")
    TvShow getByTvShowId(int tvShowId);

    @Insert
    long insert(TvShow tvShow);

    @Query("DELETE FROM tvshow WHERE tv_show_id = :tvShowId")
    int deleteByTvShowId(int tvShowId);
}
