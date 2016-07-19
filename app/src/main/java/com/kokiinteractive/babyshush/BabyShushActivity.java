package com.kokiinteractive.babyshush;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class BabyShushActivity extends AppCompatActivity {

    private boolean shushing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_shush);

        final MediaPlayer mediaPlayer = MediaPlayer.create(BabyShushActivity.this, R.raw.shush);
        mediaPlayer.setLooping(true);

        final Button startShushing = (Button) findViewById(R.id.startShushingButton);
        assert startShushing != null;
        startShushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shushing) {
                    mediaPlayer.start();
                    startShushing.setText(R.string.stop_shushing);
                } else {
                    mediaPlayer.pause();
                    startShushing.setText(R.string.start_shushing);
                }
                shushing = !shushing;
            }
        });

    }
}
