package com.idlikadai.tuneinn;

import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "Log_ListActivity";
    private static  ArrayList<TrackMetaData> songsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final String MEDIA_PATH = Environment.getExternalStorageDirectory().toString();
        Log.d(TAG,MEDIA_PATH);
        try {
            copyResourcesToStorage(MEDIA_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (songsList.size()>0)songsList.clear();
        ScanDirectory(MEDIA_PATH);
        Log.d(TAG,"Songs_Found" + songsList.size());
        addSongsToList(songsList);
    }

    public void copyResourcesToStorage(String DIR_PATH) throws IOException{
        File root_folder = new File(DIR_PATH, getResources().getString(R.string.app_name));
        if (!root_folder.exists()){
            root_folder.mkdir();
        }

        int[] SampleSoundFiles = {R.raw.sound_1,R.raw.sound_2,R.raw.sound_3};
        for (int ResID:SampleSoundFiles
                ) {
            TypedValue resource_name = new TypedValue();
            getResources().getValue(ResID,resource_name,true);
            String[] name_is = resource_name.string.toString().split("/");
            InputStream in = getResources().openRawResource(ResID);
            FileOutputStream out = new FileOutputStream(
                    root_folder.getAbsolutePath()+"/"+name_is[name_is.length-1]);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        }
    }

    public ArrayList<TrackMetaData> ScanDirectory(String DIR_PATH)  {
        File root_folder = new File(DIR_PATH);
        //Make a folder with the APPName
        if (root_folder.exists()){
            File[] all_files = root_folder.listFiles();
            if (all_files!=null){
                for (File file  :all_files) {
                    String FileName = file.getName();
                    if (!(FileName.startsWith(".")) && file.isDirectory()){
                        Log.d(TAG,"Found Directory : " + FileName);
                        ScanDirectory(file.getPath());
                    }
                    else if ( !(FileName.startsWith(".")) && ((FileName.endsWith(".mp3") || FileName.endsWith(".wav")))){
                        Log.d(TAG,"Found sound File :  " +FileName);
                        String File_Path = file.getPath();
                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(File_Path);
                        String TrackPath = file.getPath();
                        String preTrackTitle = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
                        String TrackTitle = preTrackTitle != null ? preTrackTitle :  File_Path.replace("[.]\\w*","") ;
                        String TrackArtist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)+"";
                        String TrackAlbum = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)+"";
                        String TrackGenre = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE)+"";
                        String TrackLength = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)+"";
                        String TrackYear = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR)+"";
                        TrackMetaData trackMetaData = new TrackMetaData();
                        trackMetaData.setTrackPath(TrackPath);
                        trackMetaData.setTrackTitle(TrackTitle);
                        trackMetaData.setTrackArtist(TrackArtist);
                        trackMetaData.setTrackAlbum(TrackAlbum);
                        trackMetaData.setTrackGenre(TrackGenre);
                        trackMetaData.setTrackLength(TrackLength);
                        trackMetaData.setTrackYear(TrackYear);
                        songsList.add(trackMetaData);
                    }
                }
            }
            else{
                //Either empty or no storage permissions
                Log.d(TAG,"No Permission : " + root_folder.getAbsolutePath());
            }
        }
        return songsList;
    }

    public void addSongsToList(ArrayList<TrackMetaData> songsList){
        ListView listView = (ListView) findViewById(R.id.List_Songs);

    }

    /**
     * This class stores the track Info
     */
    class TrackMetaData {

        private String TrackPath;
        private String TrackTitle;
        private String TrackArtist;
        private String TrackAlbum;
        private String TrackGenre;
        private String TrackLength;
        private String TrackYear;

        public Boolean getISsongSelected() {
            return ISsongSelected;
        }

        public void setISsongSelected(Boolean ISsongSelected) {
            this.ISsongSelected = ISsongSelected;
        }

        private Boolean ISsongSelected;

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
    }
}
