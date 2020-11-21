package id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.adhytia.carifi.people.model.People;

@Dao
public interface PeopleDao {
    @Query("SELECT * FROM people ORDER BY _id DESC")
    List<People> getAll();

    @Query("SELECT * FROM people WHERE people_id = :peopleId")
    People getByPeopleId(int peopleId);

    @Insert
    long insert(People people);

    @Query("DELETE FROM people WHERE people_id = :peopleId")
    int deleteByPeopleId(int peopleId);
}
