package me.h.shakawat.mywallpapthree.Database.DataSource;

import java.util.List;

import io.reactivex.Flowable;
import me.h.shakawat.mywallpapthree.Database.Recents;

public class RecentRespository implements IRecentsDataSource {


    private IRecentsDataSource mLocalDataSource;
    private static RecentRespository instence;


    public RecentRespository(IRecentsDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }


    public static RecentRespository getInstence(IRecentsDataSource mLocalDataSource){
        if (instence==null)
            instence = new RecentRespository(mLocalDataSource);
        return instence;
    }


    @Override
    public Flowable<List<Recents>> getAllRecents() {
        return mLocalDataSource.getAllRecents();
    }


    @Override
    public void insertRecents(Recents... recents) {
        mLocalDataSource.insertRecents(recents);
    }

    @Override
    public void updateRecents(Recents... recents) {
        mLocalDataSource.updateRecents(recents);
    }


    @Override
    public void deleteRecents(Recents... recents) {
        mLocalDataSource.deleteRecents(recents);
    }


    @Override
    public void deleteAllRecents() {
        mLocalDataSource.deleteAllRecents();
    }


}
