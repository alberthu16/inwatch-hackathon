package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.sarahwada.myapplication.models.Motion;
import com.example.sarahwada.myapplication.models.MotionsContainer;
import com.example.sarahwada.myapplication.sensorhandler.SensorHandler;


public class GameActivity extends Activity {
    private SensorHandler mSensorHandler;
    // TODO: create MotionsContainer Class
    //      Will also need a Command class
    private MotionsContainer mMotions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mSensorHandler = new SensorHandler(this);
        mMotions = new MotionsContainer();

        handleCountdownTimer();
        handleGameState();
    }

    private void handleCountdownTimer() {
        CountDownTimer startTimer = new CountDownTimer(3000, 1000) {
            TextView mDetails = (TextView) findViewById(R.id.details);

            @Override
            public void onTick(long millisUntilFinished) {
                mDetails.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mDetails.setText("");
            }
        }.start();
    }

    private void handleGameState() {
        boolean isMotionCorrect = true;
        double durationRatio = 1.00;

        while (isMotionCorrect) {
            // TODO: Implement MotionsContainer Class
            Motion currentAction = mMotions.randomize();
            updateView(currentAction);
            isMotionCorrect =
                    mSensorHandler.handle(currentAction.getUserAction(),
                            durationRatio * currentAction.getDuration());
            if (durationRatio > 0.5)
                durationRatio -= 0.01;
        }

        Intent intent = new Intent(this, EndSceneActivity.class);
        startActivity(intent);
    }

    private void updateView(Motion m) {
        TextView details = (TextView) findViewById(R.id.details);
        details.setText(m.getText());
        MediaPlayer.create(this, m.getSound()).start();
    }

}
