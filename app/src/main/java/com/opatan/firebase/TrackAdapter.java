package com.opatan.firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrackAdapter extends ArrayAdapter<TrackData> {

    private Activity context;
    private List<TrackData> trackDataList;

    public TrackAdapter(Activity context, List<TrackData> trackDataList){
        super(context, R.layout.list_item_track, trackDataList);
        this.context = context;
        this.trackDataList = trackDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item_track, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.txt_track);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.txt_rating);

        TrackData trackData = trackDataList.get(position);

        textViewName.setText(trackData.getTrackName());
        textViewGenre.setText(String.valueOf(trackData.getTrackRating()));

        return listViewItem;
    }

}