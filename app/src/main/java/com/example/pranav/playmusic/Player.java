package com.example.pranav.playmusic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Player extends AppCompatActivity {

    TextView tvSong;
    ArrayList<File> mySong;
    static MediaPlayer mediaPlayer;
    SeekBar sbSong;
    ImageView btnPause, btnNext, btnPrev, musicImage;
    int position;
    Uri uri;
    Thread updateSeekBar;
    int pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());

        tvSong = (TextView) findViewById(R.id.tvSong);
        musicImage = (ImageView) findViewById(R.id.musicImage);
        sbSong = (SeekBar) findViewById(R.id.sbSong);

        btnNext = (ImageView) findViewById(R.id.btnNext);
        btnPause = (ImageView) findViewById(R.id.btnPause);
        btnPrev = (ImageView) findViewById(R.id.btnPrev);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        mySong = (ArrayList) i.getParcelableArrayListExtra("MySong");
        position = b.getInt("pos");
        pic = b.getInt("pic");

        tvSong.setText(mySong.get(position).getName().toString());

        // For the function of the seek bar

        updateSeekBar = new Thread() {
            @Override
            public void run() {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                sbSong.setMax(totalDuration);
                while (currentPosition < totalDuration) {
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        sbSong.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                super.run();
            }
        };

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        CallMusic();
        updateSeekBar.start();

        //  Functioning of the buttons

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPause.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    btnPause.setImageResource(R.drawable.pause);
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                tvSong.setText(mySong.get(position + 1).getName().toString());
                uri = Uri.parse(mySong.get(position + 1).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                sbSong.setMax(mediaPlayer.getDuration());
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                tvSong.setText(mySong.get(position - 1).getName().toString());
                uri = Uri.parse(mySong.get(position - 1).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();
                sbSong.setMax(mediaPlayer.getDuration());
            }
        });
    }

    public void CallMusic() {
        Random r = new Random();
        int pic = r.nextInt(5) + 1;

        int ans = 0;
        if (pic == 1) {
            ans = R.drawable.bob;
        } else if (pic == 2) {
            ans = R.drawable.coldplay;
        } else if (pic == 3) {
            ans = R.drawable.eminem;
        } else if (pic == 4) {
            ans = R.drawable.hope;
        } else {
            ans = R.drawable.green_day;
        }

        //musicImage.setImageResource(ans);
        Picasso.with(this).load(ans).fit().into(musicImage);

        uri = Uri.parse(mySong.get(position).toString());
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();
        sbSong.setMax(mediaPlayer.getDuration());
    }

    @Override
    protected void onResume() {

        mediaPlayer.pause();
        super.onResume();
        mediaPlayer.start();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
