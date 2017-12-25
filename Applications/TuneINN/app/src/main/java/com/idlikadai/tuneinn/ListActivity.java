package com.idlikadai.tuneinn;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "Log_ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final String MEDIA_PATH = Environment.getExternalStorageDirectory().toString();
        Log.d(TAG,MEDIA_PATH);
        File root_folder= new File(MEDIA_PATH);
        File[] all_files = root_folder.listFiles();
        for (File file  :all_files) {
            String FileName = file.getName();
            if (!(FileName.startsWith(".")) && file.isDirectory()){
                Log.d(TAG,"");
            }
            else if ( !(FileName.startsWith(".")) && ((FileName.endsWith(".mp3") || FileName.endsWith(".wav")))){
                Log.d(TAG,FileName);
            }
        }
    }

    public ArrayList<File> ScanDirectory(){
        ArrayList<File> arrayList = new ArrayList<>();

    }
}
