package me.h.shakawat.mywallpapthree.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import me.h.shakawat.mywallpapthree.Database.Recents;

public interface IRecentsDataSource {

    Flowable<List<Recents>> getAllRecents();
    void insertRecents(Recents... recents);
    void updateRecents(Recents... recents);
    void deleteRecents(Recents... recents);
    void deleteAllRecents();

}
