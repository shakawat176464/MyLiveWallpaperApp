package me.h.shakawat.mywallpapthree.Database.LocalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import me.h.shakawat.mywallpapthree.Database.Recents;

import static me.h.shakawat.mywallpapthree.Database.LocalDatabase.LocalDatabase.DATABASE_VERSION;
@Database(entities = Recents.class,version = DATABASE_VERSION)
public abstract class LocalDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "MyWallpaper";

    public abstract RecentsDAO recentsDAO();

    private static LocalDatabase instance;

    public static LocalDatabase getInstance(Context context)
    {
        if (instance ==null)
        {
            instance = Room.databaseBuilder(context,LocalDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
