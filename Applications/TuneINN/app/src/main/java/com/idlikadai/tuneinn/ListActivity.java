package com.idlikadai.tuneinn;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "Log_ListActivity";
    private static List<TrackMetaData> songsList = new ArrayList<>();
    SongsDisplayAdapter songsDisplayAdapter = null;
    public static final String TrackINFO = null;
    public static int indexOfTrackSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final String MEDIA_PATH = Environment.getExternalStorageDirectory().toString();
        Log.d(TAG,MEDIA_PATH);
        if (MainActivity.isFirstTime){
            try {
                copyResourcesToStorage(MEDIA_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (songsList.size()>0)songsList.clear();
            ScanDirectory(MEDIA_PATH);
            Log.d(TAG,"Songs_Found" + songsList.size());
        }
        else{
            songsList.get(indexOfTrackSelected).setISsongSelected(false);
        }
        songsDisplayAdapter = new SongsDisplayAdapter(this,songsList);
        ListView listView = (ListView) findViewById(R.id.Songs_List);
        listView.setAdapter(songsDisplayAdapter);
        listView.setSelection(indexOfTrackSelected);
    }


    public void copyResourcesToStorage(String DIR_PATH) throws IOException{
        File root_folder = new File(DIR_PATH, getResources().getString(R.string.app_name));
        if (!root_folder.exists()){
            root_folder.mkdir();
        }

        int[] SampleSoundFiles = {R.raw.casio,R.raw.roland,R.raw.sound_1,R.raw.sound_2,R.raw.sound_3};
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

    public List<TrackMetaData> ScanDirectory(String DIR_PATH)  {
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
                        String TrackTitle = preTrackTitle != null ? preTrackTitle :  FileName.replaceAll("[.]\\w*","") ;
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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.song_title:
                    Integer i = (Integer)v.getTag(R.string.TitleTag);
                    indexOfTrackSelected = i;
                    songsList.get(i).setISsongSelected(!songsList.get(i).getISsongSelected());
                    songsDisplayAdapter.notifyDataSetChanged();
                    break;

            }
        }
    };

    public class SongsDisplayAdapter extends ArrayAdapter<TrackMetaData>{
        public SongsDisplayAdapter(Context context, List<com.idlikadai.tuneinn.TrackMetaData> songsLists) {
            super(context, 0, songsLists);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TrackMetaData trackMetaData= getItem(position);
            if (convertView==null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.displaysongs,parent,false);
            }
            final TextView TrackTitle = (TextView) convertView.findViewById(R.id.song_title);
            final TextView TrackAuthor = (TextView) convertView.findViewById(R.id.song_author);
            TrackTitle.setOnClickListener(onClickListener);
            TrackTitle.setTag(R.string.TitleTag,position);
            if (trackMetaData.getISsongSelected()){
                TrackTitle.setTextColor(getResources().getColor(R.color.colorTrackPlay));
                Intent intent = new Intent(getContext(),TrackInfoActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(TrackINFO,trackMetaData);
                intent.putExtras(b);
                startActivity(intent);
            }
            else{
                TrackTitle.setTextColor(getResources().getColor(R.color.colorTrackDefault));
            }
            TrackTitle.setText(trackMetaData.getTrackTitle());
            TrackAuthor.setText(trackMetaData.getTrackArtist());
            return convertView;
        }
    }
}
