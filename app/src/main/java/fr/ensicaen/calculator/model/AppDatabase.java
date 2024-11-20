package fr.ensicaen.calculator.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "database-name";

    private static AppDatabase _instance = null;

    public static AppDatabase getInstance(Context context) {
        if (_instance == null) {
            _instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }
        return _instance;
    }

    public abstract ItemDAO itemDao();
}