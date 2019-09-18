package com.opatan.firebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {

    private Activity context;
    private List<ArtistData> artistDataList;

    public ArtistAdapter(Activity context, List<ArtistData> artistDataList){
        super(context, R.layout.list_item, artistDataList);
        this.context = context;
        this.artistDataList = artistDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.nama_band);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.genre);

        ArtistData artistData = artistDataList.get(position);

        textViewName.setText(artistData.getArtistNama());
        textViewGenre.setText(artistData.getArtistGenre());

        return listViewItem;
    }
}
