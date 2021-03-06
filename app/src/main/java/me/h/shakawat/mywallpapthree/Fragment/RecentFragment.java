package me.h.shakawat.mywallpapthree.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.h.shakawat.mywallpapthree.Adapter.MyRecyclerAdapter;
import me.h.shakawat.mywallpapthree.Database.DataSource.RecentRespository;
import me.h.shakawat.mywallpapthree.Database.LocalDatabase.LocalDatabase;
import me.h.shakawat.mywallpapthree.Database.LocalDatabase.RecentsDataSource;
import me.h.shakawat.mywallpapthree.Database.Recents;
import me.h.shakawat.mywallpapthree.R;

public class RecentFragment extends Fragment {


    private static RecentFragment INSTANCE=null;

    RecyclerView recyclerView;

    Context context;
    List<Recents> recentsList;
    MyRecyclerAdapter adapter;


    ////Room Database
    CompositeDisposable compositeDisposable;
    RecentRespository recentRespository;

    @SuppressLint("ValidFragment")
    public RecentFragment(Context context) {
        // Required empty public constructor
        this.context = context;


        ///init roomdatabase
        compositeDisposable = new CompositeDisposable();
        LocalDatabase database = LocalDatabase.getInstance(context);
        recentRespository = RecentRespository.getInstence(RecentsDataSource.getInstance(database.recentsDAO()));


    }


    public static RecentFragment getInstance(Context context){

        if (INSTANCE ==null)
            INSTANCE = new RecentFragment(context);
        return INSTANCE;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent, container, false);
        recyclerView = view.findViewById(R.id.recycler_recent);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recentsList = new ArrayList<>();

        adapter = new MyRecyclerAdapter(context,recentsList);
        recyclerView.setAdapter(adapter);
        
        
        loadRecents();


        return view;
    }

    private void loadRecents() {

        Disposable disposable = recentRespository.getAllRecents()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Recents>>() {
                    @Override
                    public void accept(List<Recents> recents) throws Exception {
                        onGetAllRecentsSuccess(recents);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ERROR",throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);

    }

    private void onGetAllRecentsSuccess(List<Recents> recents) {

        recentsList.clear();
        recentsList.addAll(recents);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}
