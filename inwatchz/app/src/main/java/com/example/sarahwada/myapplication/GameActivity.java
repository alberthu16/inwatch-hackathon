package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Intent;
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

        CountDownTimer startTimer = new CountDownTimer(3000, 1000) {
            TextView mTimer = (TextView) findViewById(R.id.timer);

            @Override
            public void onTick(long millisUntilFinished) {
                mTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mTimer.setText("");
            }
        }.start();

        handleGameState();
    }

    private void handleGameState() {
        boolean isMotionCorrect = true;
        double durationRatio = 1.00;

        while (isMotionCorrect) {
            // TODO: inflate views of randomly chosen motion
            Motion currentCommand = mMotions.randomize();
            updateView(currentCommand);
            isMotionCorrect = mSensorHandler.handle(currentCommand.getUserAction(),
                    durationRatio * );
            if (durationRatio > 0.5)
                durationRatio -= 0.01;
        }

        Intent intent = new Intent(this, EndSceneActivity.class);
        startActivity(intent);
    }

    private void updateView(Motion m) {

    }

}
