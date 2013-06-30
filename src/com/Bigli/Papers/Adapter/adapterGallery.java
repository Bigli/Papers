package com.Bigli.Papers.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.Bigli.Papers.R;

/**
 * By Bigli
 */
public class adapterGallery extends BaseAdapter{

    int[] gallery_image;
    Context context;
    public adapterGallery(Context context, int[]gallery_image)
    {
        this.gallery_image = gallery_image;
        this.context = context;
    }

    @Override
    public int getCount() {
        return gallery_image.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView icon = new ImageView(context);
        Resources res = context.getResources();
        icon.setBackgroundResource(R.drawable.gallery_style);
        icon.setPadding(10, 2, 10, 2);
        icon.setMinimumHeight(res.getInteger(R.integer.height_icon));
        icon.setMinimumWidth(res.getInteger(R.integer.width_icon));
        icon.setImageResource(gallery_image[position]);
        return icon;
    }
}
