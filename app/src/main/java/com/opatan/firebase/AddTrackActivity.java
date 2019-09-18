package com.opatan.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    private SeekBar seekBarRating;
    private TextView textViewArtistName;
    private EditText EditTextTrackName;
    private Button addTrack;
    private ListView listView;

    DatabaseReference databaseTracks;

    List<TrackData> trackDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        textViewArtistName = (TextView) findViewById(R.id.namaArtist);
        EditTextTrackName = (EditText) findViewById(R.id.input_nama);
        listView = (ListView) findViewById(R.id.list_view);
        addTrack = (Button) findViewById(R.id.submit);
        Intent intent = getIntent();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String nama = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(nama);

        databaseTracks = FirebaseDatabase.getInstance().getReference("track").child(id);

        addTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();
            }
        });

        trackDataList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                trackDataList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    TrackData track = postSnapshot.getValue(TrackData.class);
                    trackDataList.add(track);
                }
                TrackAdapter trackListAdapter = new TrackAdapter(AddTrackActivity.this, trackDataList);
                listView.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack() {

        String trackName = EditTextTrackName.getText().toString().trim();
        int Rating = seekBarRating.getProgress();

        if (!TextUtils.isEmpty(trackName)){

            String id = databaseTracks.push().getKey();

            TrackData trackData = new TrackData(id, trackName, Rating);

            databaseTracks.child(id).setValue(trackData);

            Toast.makeText(this, "Track telah disimpan", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Track Harus Diisi", Toast.LENGTH_SHORT).show();
        }

    }
}
