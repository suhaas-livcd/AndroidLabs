package com.idlikadai.tuneinn;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suhaas on 12/30/2017.
 */

public class TrackMetaData implements Parcelable {
    private String TrackPath;
    private String TrackTitle;
    private String TrackArtist;
    private String TrackAlbum;
    private String TrackGenre;
    private String TrackLength;
    private String TrackYear;
    private Boolean ISsongSelected = false;

    protected TrackMetaData(Parcel in) {
        TrackPath = in.readString();
        TrackTitle = in.readString();
        TrackArtist = in.readString();
        TrackAlbum = in.readString();
        TrackGenre = in.readString();
        TrackLength = in.readString();
        TrackYear = in.readString();
    }

    public static final Creator<TrackMetaData> CREATOR = new Creator<TrackMetaData>() {
        @Override
        public TrackMetaData createFromParcel(Parcel in) {
            return new TrackMetaData(in);
        }

        @Override
        public TrackMetaData[] newArray(int size) {
            return new TrackMetaData[size];
        }
    };

    public TrackMetaData() {

    }

    public Boolean getISsongSelected() {
        return ISsongSelected;
    }

    public void setISsongSelected(Boolean ISsongSelected) {
        this.ISsongSelected = ISsongSelected;
    }



    public String getTrackPath() {
        return TrackPath;
    }

    public void setTrackPath(String trackPath) {
        TrackPath = trackPath;
    }

    public String getTrackTitle() {
        return TrackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        TrackTitle = trackTitle;
    }

    public String getTrackArtist() {
        return TrackArtist;
    }

    public void setTrackArtist(String trackArtist) {
        TrackArtist = trackArtist;
    }

    public String getTrackAlbum() {
        return TrackAlbum;
    }

    public void setTrackAlbum(String trackAlbum) {
        TrackAlbum = trackAlbum;
    }

    public String getTrackGenre() {
        return TrackGenre;
    }

    public void setTrackGenre(String trackGenre) {
        TrackGenre = trackGenre;
    }

    public String getTrackLength() {
        return TrackLength;
    }

    public void setTrackLength(String trackLength) {
        TrackLength = trackLength;
    }

    public String getTrackYear() {
        return TrackYear;
    }

    public void setTrackYear(String trackYear) {
        TrackYear = trackYear;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TrackPath);
        dest.writeString(TrackTitle);
        dest.writeString(TrackArtist);
        dest.writeString(TrackAlbum);
        dest.writeString(TrackGenre);
        dest.writeString(TrackLength);
        dest.writeString(TrackYear);
    }
}
