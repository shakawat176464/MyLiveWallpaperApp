package me.h.shakawat.mywallpapthree.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import me.h.shakawat.mywallpapthree.Interface.ItemClickListener;
import me.h.shakawat.mywallpapthree.R;

public class ListWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    ItemClickListener itemClickListener;

    public ImageView wallpaper;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ListWallpaperViewHolder(@NonNull View itemView) {
        super(itemView);

        wallpaper = itemView.findViewById(R.id.listWallpaperImage);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition());
    }
}
