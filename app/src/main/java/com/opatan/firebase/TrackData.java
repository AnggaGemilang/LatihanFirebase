package com.opatan.firebase;

public class TrackData {

    private String trackId, trackName;
    private int trackRating;

    public TrackData(){

    }

    public TrackData(String trackId, String trackName, int trackRating) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.trackRating = trackRating;
    }

    public String getTrackId() {
        return trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public int getTrackRating() {
        return trackRating;
    }
}
