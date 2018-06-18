package com.something.kteam.learnandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by uyguyg on 9/14/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Video> listVideo;


    public CustomAdapter(Context context, ArrayList<Video> listVideo) {
        this.context = context;
        this.listVideo = listVideo;
    }


    @Override
    public int getCount() {
        return listVideo.size();
    }

    @Override
    public Object getItem(int i) {
        return listVideo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listVideo.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       if(view == null) {
           LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = inflater.inflate(R.layout.item, viewGroup, false);
       }
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        TextView txtTimes = (TextView) view.findViewById(R.id.txt_times);
        Video videoInfo = listVideo.get(i);
        txtTitle.setText(videoInfo.getTitle());
        txtTimes.setText(videoInfo.getTime());
        return view;
    }
}
