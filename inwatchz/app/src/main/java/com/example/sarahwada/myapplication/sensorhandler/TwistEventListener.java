package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.example.sarahwada.myapplication.R;

/**
 *
 */
public class TwistEventListener extends ActionEventListener {
    private boolean initialized = false;

    float[] orientationMatrix = new float[3];
    float[] rotationMatrix = new float[9];

    public TwistEventListener(SensorManager sensorManager, Context context) {
        super(sensorManager, context);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Gather all event data
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            if (!success) {
                getRotationVector(event);
            }
        }
    }

    private void getRotationVector(SensorEvent event) {
        SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
        SensorManager.getOrientation(rotationMatrix, orientationMatrix);

        // 1 radian = 57.2957795 degrees
        // orientationMatrix = [yaw (z-axis), pitch (x-axis), roll (y-axis)]
        float yaw = orientationMatrix[0] * 57.2957795f;
        float pitch = orientationMatrix[1] * 57.2957795f;
        float roll = orientationMatrix[2] * 57.2957795f;

        // Make sure user is in starting position
        if (!initialized && isStartingPosition(pitch, roll)) {
            Log.i("Starting position", String.format("Pitch: %f, Roll: %f", pitch, roll));
            initialized = true;
        // Check if user is in ending position
        } else if (initialized && isEndingPosition(pitch, roll)) {
            Log.i("Ending position", "SUCCESS");
            Log.i("Ending position", String.format("Pitch: %f, Roll: %f", pitch, roll));
            this.success = true;
            Toast.makeText(context, "TWIST! ", Toast.LENGTH_SHORT).show();

            //TODO: play media sound
            //MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.boom);
            //mediaPlayer.start();
        }
    }

    private boolean isStartingPosition(float pitch, float roll) {
        return ((Math.abs(pitch) < 20) &&
                (Math.abs(roll) < 20));
    }

    private boolean isEndingPosition(float pitch, float roll) {
        return ((pitch > 65) &&
                (pitch < 95) &&
                (Math.abs(roll) < 50));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
