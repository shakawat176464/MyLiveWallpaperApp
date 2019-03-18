package me.h.shakawat.mywallpapthree.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.h.shakawat.mywallpapthree.Common.Common;
import me.h.shakawat.mywallpapthree.Database.Recents;
import me.h.shakawat.mywallpapthree.Interface.ItemClickListener;
import me.h.shakawat.mywallpapthree.ListWallpaperActivity;
import me.h.shakawat.mywallpapthree.Model.WallpaperItem;
import me.h.shakawat.mywallpapthree.R;
import me.h.shakawat.mywallpapthree.ViewHolder.ListWallpaperViewHolder;
import me.h.shakawat.mywallpapthree.ViewWallpaperActivity;

public class MyRecyclerAdapter extends RecyclerView.Adapter<ListWallpaperViewHolder> {

    private Context context;
    private List<Recents> recents;

    public MyRecyclerAdapter(Context context, List<Recents> recents) {
        this.context = context;
        this.recents = recents;
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

    @Override
    public void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, final int position) {

        Picasso.get()
                .load(recents.get(position).getImageLink())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.wallpaper, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(recents.get(position).getImageLink())
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
                Intent intent = new Intent(context,ViewWallpaperActivity.class);
                WallpaperItem wallpaperItem = new WallpaperItem();
                wallpaperItem.setCategoryId(recents.get(position).getCategoryId());
                wallpaperItem.setImageLink(recents.get(position).getImageLink());
                Common.select_background = wallpaperItem;
                Common.select_background_key = recents.get(position).getKey();
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recents.size();
    }
}
