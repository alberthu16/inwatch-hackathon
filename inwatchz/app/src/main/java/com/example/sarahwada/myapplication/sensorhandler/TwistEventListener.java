package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.ImageView;

import com.example.sarahwada.myapplication.R;

/**
 * Event listener for the twist event.
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
        float pitch = orientationMatrix[1] * 57.2957795f;
        float roll = orientationMatrix[2] * 57.2957795f;

        // Make sure user is in starting position
        if (!initialized && isStartingPosition(pitch, roll)) {
            initialized = true;
        // Check if user is in ending position
        } else if (initialized && isEndingPosition(pitch)) {
            this.success = true;
            this.context.setIsMotionCorrect(true);

            ImageView image = (ImageView) this.context.findViewById(R.id.image);
            image.setImageResource(R.drawable.twist_complete);
            MediaPlayer.create(this.context, R.raw.twist_effect).start();
        }
    }

    private boolean isStartingPosition(float pitch, float roll) {
        return ((Math.abs(pitch) < 50) && (Math.abs(roll) < 50));
    }

    private boolean isEndingPosition(float pitch) {
        return ((pitch > 60) && (pitch < 100));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
