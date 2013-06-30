package com.Bigli.Papers.Adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.Bigli.Papers.Corrector;
import com.Bigli.Papers.Data.DataCats;
import com.Bigli.Papers.R;

import java.util.ArrayList;

/**
 * by Bigli
 */
public class adapterCats extends BaseAdapter {

    Corrector corrector = new Corrector();
    ArrayList<DataCats> dataCats;
    Context context;
    public adapterCats(Context context, ArrayList<DataCats> arrayDataCats)
    {
        dataCats = arrayDataCats;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataCats.size();
    }

    @Override
    public DataCats getItem(int position) {
        return dataCats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_cats, null);
        }

        ImageView Cats_image = (ImageView) convertView.findViewById(R.id.imageCats);
        TextView Cats_name = (TextView) convertView.findViewById(R.id.cats_name);
        TextView Papers_count = (TextView) convertView.findViewById(R.id.papers_count);

        Cats_image.setImageResource(corrector.getImageId(context, getItem(position).get_image()));
        Cats_name.setText(getItem(position).get_name());
        Papers_count.setText(String.valueOf(getItem(position).get_papers_count()));
        return convertView;
    }
}
