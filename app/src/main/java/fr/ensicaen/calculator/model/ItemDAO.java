package fr.ensicaen.calculator.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDAO {
    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    void delete();
}
