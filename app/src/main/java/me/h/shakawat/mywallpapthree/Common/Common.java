package me.h.shakawat.mywallpapthree.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.h.shakawat.mywallpapthree.Model.WallpaperItem;

public class Common {

    public static final String STR_CATEGORY_BACKGROUND="CategoryBackground_Three";
    public static final String STR_WALLPAPER="Background_Three";
    public static String CATEGORY_SELECTED;
    public static String CATEGORY_ID_SELECTED;

    public static final int PERMISSION_REQUEST_CODE = 1000;

    public static WallpaperItem select_background = new WallpaperItem();

    public static String select_background_key;



    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0; i<info.length; i++ )
                {
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }



}
