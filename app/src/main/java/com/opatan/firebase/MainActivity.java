package com.opatan.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artist name";
    public static final String ARTIST_ID = "artist id";

    private EditText editNama;
    private Button submit;
    Spinner comboBox;
    DatabaseReference databaseReference;
    ListView listView;
    List<ArtistData> artistDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        artistDataList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("artist");

        editNama = findViewById(R.id.input_nama);
        submit = findViewById(R.id.submit);
        comboBox = findViewById(R.id.spiner);

        listView = findViewById(R.id.list_view);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArtistData artistData = artistDataList.get(i);
                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class);
                intent.putExtra(ARTIST_ID, artistData.getArtistId());
                intent.putExtra(ARTIST_NAME, artistData.getArtistNama());

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArtistData artist = artistDataList.get(i);
                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistNama());
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                artistDataList.clear();

                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    ArtistData artistData = artistSnapshot.getValue(ArtistData.class);

                    artistDataList.add(artistData);

                }

                ArtistAdapter adapter = new ArtistAdapter(MainActivity.this, artistDataList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean deleteArtist(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artist").child(id);

        dR.removeValue();

        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("track").child(id);

        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private boolean updateArtist(String id, String name, String genre) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artist").child(id);

        ArtistData artist = new ArtistData(id, name, genre);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();

                if (!TextUtils.isEmpty(name)) {
                    updateArtist(artistId, name, genre);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                b.dismiss();
            }
        });
    }

    private void addArtist(){

        String name = editNama.getText().toString().trim();
        String spinner = comboBox.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){

            String id = databaseReference.push().getKey();

            ArtistData artistData = new ArtistData(id, name, spinner);

            databaseReference.child(id).setValue(artistData);

            Toast.makeText(getApplicationContext(), "Data ditambahkan", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Masukkan data", Toast.LENGTH_SHORT).show();
        }

    }

}
