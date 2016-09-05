package com.kokiinteractive.babyshush;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class BabyShushActivity extends AppCompatActivity {

    private static final long TWENTY_MINUTES = 1200000;
    private static final long FORTY_MINUTES = 2400000;

    private boolean shushing = false;
    private Timer timerWithTask;
    private CountDownTimer countdownTimer;

    private MediaPlayer mediaPlayer;
    private Button startShushing;
    private TextView countdownText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_shush);

        mediaPlayer = MediaPlayer.create(BabyShushActivity.this, R.raw.quick_shushes);
        mediaPlayer.setLooping(true);

        countdownText = (TextView) findViewById(R.id.countdownText);
        assert countdownText != null;

        startShushing = (Button) findViewById(R.id.startShushingButton);
        assert startShushing != null;
        startShushing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shushing) {
                    startShushing();
                } else {
                    stopShushing();
                }
            }
        });

        final Button timer20min = (Button) findViewById(R.id.timer20min);
        assert timer20min != null;
        timer20min.setOnClickListener(onClickTimerForPeriod(TWENTY_MINUTES));

        final Button timer40min = (Button) findViewById(R.id.timer40min);
        assert timer40min != null;
        timer40min.setOnClickListener(onClickTimerForPeriod(FORTY_MINUTES));

        final Button forever = (Button) findViewById(R.id.forever);
        assert forever != null;
        forever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimers();
            }
        });
    }

    private View.OnClickListener onClickTimerForPeriod(final long period) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimers();
                timerWithTask = new Timer("timer", true);
                timerWithTask.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopShushing();
                            }
                        });
                    }
                }, period);

                countdownTimer = new CountDownTimer(period, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long minutes = millisUntilFinished / 1000 / 60;
                        long seconds = millisUntilFinished / 1000 % 60;
                        // display remaining time
                        countdownText.setText(minutes + " : " + seconds);
                    }

                    @Override
                    public void onFinish() {
                        countdownText.setText("");
                    }
                }.start();
            }
        };
    }

    private void startShushing() {
        mediaPlayer.start();
        startShushing.setText(R.string.stop_shushing);
        shushing = true;
    }

    private void stopShushing() {
        mediaPlayer.pause();
        startShushing.setText(R.string.start_shushing);
        stopTimers();
        shushing = false;
    }

    private void stopTimers() {
        if (timerWithTask != null) {
            timerWithTask.cancel();
            timerWithTask = null;
        }
        if (countdownTimer != null) {
            countdownTimer.cancel();
            countdownTimer = null;
            countdownText.setText("");
        }
    }
}
