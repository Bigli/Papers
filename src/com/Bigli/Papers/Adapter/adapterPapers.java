package com.Bigli.Papers.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.Bigli.Papers.Corrector;
import com.Bigli.Papers.Data.DataPapers;
import com.Bigli.Papers.R;

import java.util.ArrayList;

/**
 * By Bigli
 */
public class adapterPapers extends BaseAdapter {

    Corrector correcot = new Corrector();
    ArrayList<DataPapers> dataPapers;
    Context context;

    public adapterPapers(Context context, ArrayList<DataPapers> arrayDataPapers)
    {
        dataPapers = arrayDataPapers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataPapers.size();
    }

    @Override
    public DataPapers getItem(int position) {
        return dataPapers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_papers, null);
        }

        TextView papers_name = (TextView) convertView.findViewById(R.id.papers_name);
        ImageView papers_image = (ImageView) convertView.findViewById(R.id.imagePapers);
        TextView short_text = (TextView) convertView.findViewById(R.id.short_papersText);
        TextView papers_data = (TextView) convertView.findViewById(R.id.papers_data);

        papers_name.setText(getItem(position).get_name());
        papers_image.setImageResource(correcot.getImageId(context, getItem(position).get_image()));
        short_text.setText(correcot.getShort(getItem(position).get_text()));
        papers_data.setText(getItem(position).get_time());
        return convertView;
    }
}
