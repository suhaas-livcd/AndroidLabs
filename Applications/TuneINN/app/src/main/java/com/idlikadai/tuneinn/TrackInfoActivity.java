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
import android.widget.Toast;

public class TrackInfoActivity extends AppCompatActivity {
    private static final String TAG = "APP_DBG";
    TrackMetaData trackMetaData = null;
    private static MediaPlayer mediaPlayer = null;
    private static String CurrentSong = "";
    private static String PreviousSong =  "";
    private static boolean isPause = false;
    private Handler mHandler = null;
    SeekBar seekBar = null;
    TextView startTime = null , endTime = null;
    TextView TrackTitle = null;
    TextView TrackArtist = null;
    TextView TrackAlbum = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Track Info Created");
        MainActivity.isFirstTime = false;
        setContentView(R.layout.activity_menu);
        Bundle bundle = getIntent().getExtras();
        trackMetaData = bundle.getParcelable(ListActivity.TrackINFO);
        CurrentSong = trackMetaData.getTrackPath();
        TrackTitle = (TextView) findViewById(R.id.DTitle);
        TrackTitle.setText(trackMetaData.getTrackTitle());
        TrackArtist = (TextView) findViewById(R.id.DArtist);
        TrackArtist.setText(trackMetaData.getTrackArtist());
        TrackAlbum = (TextView) findViewById(R.id.DAlbum);
        TrackAlbum.setText(trackMetaData.getTrackAlbum());
        if (mediaPlayer!=null){
            if(CurrentSong.equals(PreviousSong)){
                //continue
            }
            else{
                //song changed
                mediaPlayer.reset();
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(trackMetaData.getTrackPath()));
                //mediaPlayer.setLooping(true);
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
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        startTime = (TextView) findViewById(R.id.TextStartTime);
        endTime = (TextView) findViewById(R.id.TextEndTime);
        int Total_Length = Integer.parseInt(trackMetaData.getTrackLength());
        int end_minutes = (Total_Length/1000)/60;
        endTime.setText(end_minutes+":"+(Total_Length-(60*end_minutes)));
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


        /**
         * This method is invoked when the media player is stopped.
         */
        ImageView imageStop = (ImageView) findViewById(R.id.Image_Stop);
        imageStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               StopPlaying();
           }
        });

        ImageView imagelistSongs = (ImageView) findViewById(R.id.ImageListButton);
        imagelistSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviousSong = trackMetaData.getTrackPath();
                Intent intent = new Intent(TrackInfoActivity.this,ListActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * This method is invoked when the user presses the back chevron button on the screen
     * Previous songs list is invoked
     * @param view
     */
    public void GoToPreviousActvity(View view){
        Toast.makeText(getApplicationContext(),"Back Button Disabled",Toast.LENGTH_SHORT).show();
    }

    private void StopPlaying(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        RestoreDeafults();
    }

    private void RestoreDeafults(){
        if (seekBar!=null){
            seekBar.setProgress(0);
        }
        if (startTime!=null && endTime!=null){
            startTime.setText("0:00");
            endTime.setText("0:00");
        }
        TrackTitle.setText("No track selected");
        TrackArtist.setText("Artist");
        TrackAlbum.setText("Album");
    }
}
