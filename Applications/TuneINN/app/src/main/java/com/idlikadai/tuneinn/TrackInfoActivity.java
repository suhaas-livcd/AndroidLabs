package com.idlikadai.tuneinn;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TrackInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void GoToPreviousActvity(View view){
        Intent intent = new Intent(TrackInfoActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void ChnagePlaytoPause(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.ImageViewMenuPlay);
        imageView.setImageResource(R.drawable.ic_pause_black_24px);
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.sound_1);
        mediaPlayer.start();
    }
}