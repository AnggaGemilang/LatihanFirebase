package com.opatan.firebase;

public class ArtistData {

    private String artistId, artistNama, artistGenre;

    public ArtistData(){

    }

    public ArtistData(String artistId, String artistNama, String artistGenre) {
        this.artistId = artistId;
        this.artistNama = artistNama;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistNama() {
        return artistNama;
    }

    public String getArtistGenre() {
        return artistGenre;
    }
}
