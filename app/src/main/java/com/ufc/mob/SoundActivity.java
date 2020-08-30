package com.ufc.mob;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SoundActivity extends AppCompatActivity {

    ImageButton playerBtn;
    SeekBar audioTimeBar;
    SeekBar volumeBar;
    TextView currentTimeTxt;
    TextView remainingTimeTxt;

    MediaPlayer mp;
    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        playerBtn = (ImageButton) findViewById(R.id.playBtn);
        currentTimeTxt = (TextView) findViewById(R.id.currentTimeText);
        remainingTimeTxt = (TextView) findViewById(R.id.remaineTimeText);

//        Create Media Player
        mp = MediaPlayer.create(this,R.raw.we_go_one_piece_opening_15_piano_synthesia);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        totalTime = mp.getDuration();

//        Init Audio Time Bar and Apply max Value,change listiner
        audioTimeBar = (SeekBar) findViewById(R.id.audioTimeBar);
        audioTimeBar.setMax(totalTime);
        audioTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    if (fromUser){
                        mp.seekTo(progress);
                        audioTimeBar.setProgress(progress);
                    }
                }catch (Exception err){
                    Log.d("Error",err.getMessage()+"--1");
                    throw err;
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        Init Volume Bar and Apply change listiner
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    float volume = progress/100f;
                    mp.setVolume(volume,volume);
                }catch (Exception err){
                    Log.d("Error",err.getMessage()+"--2");
                    throw err;
                }

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (mp != null){
                    try {
                        int currentTime = mp.getCurrentPosition();
                        updateUi(currentTime);
                        Thread.sleep(1000);
                    }catch (Exception err){
                        Log.d("Error",err.getMessage()+"Tread");
                    }
                }
            }
        }.start();
    }
    private void updateUi(final int currentPosition){
//           Set Bar Current Position
        Runnable timeBarUpdateRun  = new Runnable() {
            @Override
            public void run() {
                audioTimeBar.setProgress(currentPosition);
            }
        };
        audioTimeBar.post(timeBarUpdateRun);

        final String currentTime = createTimeLabel(currentPosition);
        Runnable currentTimeRun = new Runnable() {
            @Override
            public void run() {
                currentTimeTxt.setText(currentTime);
            }
        };
        currentTimeTxt.post(currentTimeRun);

        final String remainingTime = createTimeLabel(totalTime - currentPosition);
        Runnable remainingTimeRun = new Runnable() {
            @Override
            public void run() {
                remainingTimeTxt.setText("-"+remainingTime);
            }
        };
        remainingTimeTxt.post(remainingTimeRun);
    }

    private String createTimeLabel(int time){
        String timeLabel = "";
        int min = time / 1000 / 60;
        int seg = time / 1000 % 60;

        timeLabel += min+":";
        timeLabel += (seg < 10)?"0"+seg : seg;
        return timeLabel;
    }

    public void playerOnClick(View view){
        if (!mp.isPlaying()){
            mp.start();
            playerBtn.setImageResource(R.drawable.pause);
        }else {
            mp.pause();
            playerBtn.setImageResource(R.drawable.play);
        }
    }

    @Override
    protected void onDestroy() {
        if (mp != null){
            mp.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (mp != null && mp.isPlaying()){
            mp.pause();
            playerBtn.setImageResource(R.drawable.play);
        }
        super.onPause();
    }
}