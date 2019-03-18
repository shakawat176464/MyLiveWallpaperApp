package me.h.shakawat.mywallpapthree.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import me.h.shakawat.mywallpapthree.Common.Common;
import me.h.shakawat.mywallpapthree.Interface.ItemClickListener;
import me.h.shakawat.mywallpapthree.ListWallpaperActivity;
import me.h.shakawat.mywallpapthree.Model.WallpaperItem;
import me.h.shakawat.mywallpapthree.R;
import me.h.shakawat.mywallpapthree.ViewHolder.ListWallpaperViewHolder;
import me.h.shakawat.mywallpapthree.ViewWallpaperActivity;

public class DailyPopularFragment extends Fragment {


    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference categoryBackgrounds;
    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem,ListWallpaperViewHolder> adapter;


    private static DailyPopularFragment INSTANCE=null;


    public DailyPopularFragment() {
        // Required empty public constructor
        ///init firebase
        database = FirebaseDatabase.getInstance();
        categoryBackgrounds = database.getReference(Common.STR_WALLPAPER);

        Query query = categoryBackgrounds.orderByChild("viewCount")
                .limitToFirst(10);////get 10 item have biggest view count
        /////////.limitToLast(10)....this line change my me....

        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>()
                .setQuery(query,WallpaperItem.class)
                .build();
        adapter = new FirebaseRecyclerAdapter<WallpaperItem, ListWallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, int position, @NonNull final WallpaperItem model) {

                Picasso.get()
                        .load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.wallpaper, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get()
                                        .load(model.getImageLink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.wallpaper, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Log.e("Error","Could not fetch image");
                                            }
                                        });
                            }
                        });


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        /////code later
                        Intent intent = new Intent(getActivity(),ViewWallpaperActivity.class);
                        Common.select_background = model;
                        Common.select_background_key = adapter.getRef(position).getKey();
                        startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_wallpaper_list_item,parent,false);
                int height = parent.getMeasuredHeight()/2;
                itemView.setMinimumHeight(height);

                return new ListWallpaperViewHolder(itemView);

            }
        };

    }
    public static DailyPopularFragment getInstance(){

        if (INSTANCE ==null)
            INSTANCE = new DailyPopularFragment();
        return INSTANCE;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_popular, container, false);

        recyclerView = view.findViewById(R.id.recycler_Daily_popular);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //////Because firebase return ascessing sort list so we need reverse recyclerciew to show lagest item is firs
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        
        
        loadTrendingList();

        return view;
    }

    private void loadTrendingList() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter!=null)
            adapter.startListening();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter!=null)
            adapter.startListening();

    }
}
