package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sarahwada.myapplication.models.Motion;
import com.example.sarahwada.myapplication.models.MotionsContainer;
import com.example.sarahwada.myapplication.sensorhandler.SensorHandler;


public class GameActivity extends Activity {
    private SensorHandler mSensorHandler;
    private MotionsContainer mMotions;

    private Motion currentAction;
    private long currentDuration;

    private boolean isMotionCorrect = true;
    private long durationRatio = 100;
    private int score = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorHandler = new SensorHandler(this);
        mMotions = new MotionsContainer();

        handleCountdownTimer();
    }

    private void handleCountdownTimer() {
        CountDownTimer startTimer = new CountDownTimer(3000, 500) {
            TextView mDetails = (TextView) findViewById(R.id.details);

            @Override
            public void onTick(long millisUntilFinished) {
                mDetails.setText("" + (millisUntilFinished / 1000 + 1));
            }

            @Override
            public void onFinish() {
                mDetails.setText("");
                mDetails.setTextSize(34);
                handleGameState();
            }
        }.start();
    }

    public void setIsMotionCorrect(boolean bool) {
        Log.i("GameActivity", String.format("setIsMotionCorrect: %s", bool));
        this.isMotionCorrect = bool;
    }

    private void handleGameState() {
        // Schedule the initial runnable
        this.currentAction = mMotions.random();
        this.currentDuration = (durationRatio * currentAction.getDuration())/100;
        Log.i("GameActivity", String.format("schedule first runnable, duration:%d", currentDuration));
        final Handler handler = new Handler();

        final Runnable playTurnRunnable = new Runnable() {
            @Override
            public void run() {
                mSensorHandler.cleanup();

                if (!isMotionCorrect) {
                    Log.i("GameActivity", "runnable: end game");
                    endGame();
                } else {
                    Log.i("GameActivity", "starting current action handling");
                    isMotionCorrect = false;
                    score += 1;
                    updateView(currentAction);
                    mSensorHandler.handle(currentAction.getUserAction());

                    // schedule the next a runnable
                    currentDuration = (durationRatio * currentAction.getDuration())/100;
                    Log.i("GameActivity", String.format("schedule next runnable, currActionDurration:%d", currentAction.getDuration()));
                    Log.i("GameActivity", String.format("schedule next runnable, durationRatio:%d", durationRatio));
                    Log.i("GameActivity", String.format("schedule next runnable, duration:%d", currentDuration));
                    handler.postDelayed(this, currentDuration);

                    if (durationRatio > 50) {
                        durationRatio -= 1;
                    }
                    currentAction = mMotions.random();

                }
            }
        };

        handler.post(playTurnRunnable);
    }

    public void endGame() {
        Intent intent = new Intent(this, EndSceneActivity.class);
        intent.putExtra("score", String.format("%d", score));
        startActivity(intent);
    }

    private void updateView(Motion m) {
        TextView details = (TextView) findViewById(R.id.details);
        details.setText(m.getText());
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(m.getImage());
        MediaPlayer.create(this, m.getSound()).start();
    }

    @Override
    public void onBackPressed() {
    }

}
