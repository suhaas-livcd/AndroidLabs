package com.idlikadai.tuneinn;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class TrackInfoActivity extends AppCompatActivity {
    TrackMetaData trackMetaData = null;
    private static MediaPlayer mediaPlayer = null;
    private static String CurrentSong = "";
    private static String PreviousSong =  "";
    private static boolean isPause = false;
    private Handler mHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.isFirstTime = false;
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        trackMetaData = bundle.getParcelable(ListActivity.TrackINFO);
        CurrentSong = trackMetaData.getTrackPath();
        TextView TrackTitle = (TextView) findViewById(R.id.DTitle);
        TrackTitle.setText(trackMetaData.getTrackTitle());
        TextView TrackArtist = (TextView) findViewById(R.id.DArtist);
        TrackArtist.setText(trackMetaData.getTrackArtist());
        TextView TrackAlbum = (TextView) findViewById(R.id.DAlbum);
        TrackAlbum.setText(trackMetaData.getTrackAlbum());
        if (mediaPlayer!=null){
            if(CurrentSong.equals(PreviousSong)){
                //continue
            }
            else{
                //song changed
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(trackMetaData.getTrackPath()));
                mediaPlayer.setLooping(true);
                CurrentSong = trackMetaData.getTrackPath();
                mediaPlayer.start();
            }
        }
        else{
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(trackMetaData.getTrackPath()));
            mediaPlayer.setLooping(true);
            CurrentSong = trackMetaData.getTrackPath();
            mediaPlayer.start();
        }


        final ImageView PauseButton = (ImageView) findViewById(R.id.Button_Pause);
        PauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPause){
                    if (mediaPlayer!=null && mediaPlayer.isPlaying()) mediaPlayer.pause();
                    isPause = false;
                    PauseButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else{
                    if (mediaPlayer!=null && !(mediaPlayer.isPlaying())) mediaPlayer.start();
                    isPause = true;
                    PauseButton.setImageResource(R.drawable.ic_pause_black_24px);
                }
            }
        });

        mHandler = new Handler();
        final SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        final TextView startTime = (TextView) findViewById(R.id.TextStartTime);
        final TextView EndTime = (TextView) findViewById(R.id.TextEndTime);
        int Total_Length = Integer.parseInt(trackMetaData.getTrackLength());
        int end_minutes = (Total_Length/1000)/60;
        EndTime.setText(end_minutes+":"+(Total_Length-(60*end_minutes)));
        TrackInfoActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(currentPosition);
                    int minutes = currentPosition/60;
                    startTime.setText(minutes+":"+(currentPosition-(minutes*60)));
                }
                mHandler.postDelayed(this,1000);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ImageView imageStop = (ImageView) findViewById(R.id.Image_Stop);
        imageStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void GoToPreviousActvity(View view){
        PreviousSong = trackMetaData.getTrackPath();
        Intent intent = new Intent(TrackInfoActivity.this,ListActivity.class);
        startActivity(intent);
    }
}
