package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class PushEventListener extends ActionEventListener {


    /* Current acceleration values */
    float xAccel;
    float yAccel;
    float zAccel;

    /* Previous acceleration values */
    float xPrev;
    float yPrev;
    float zPrev;

    /* Supress first motion? */
    boolean firstUpdate = true;

    /* Push Threshold */
    final float pushThreshold = 6.3f;
    float magnitude;

    /* Has a motion started */
    boolean pushInitiated = false;

    public PushEventListener(SensorManager sensorManager, Context context) {
        super(sensorManager, context);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float[] values = event.values;
            float[] gravity = {0, 0, 0};

            float alpha = 0.8f;

            gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
            gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
            gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

            values[0] = event.values[0] - gravity[0];
            values[1] = event.values[1] - gravity[1];
            values[2] = event.values[2] - gravity[2];

            /* Detected movement */
            float x = values[0];
            float y = values[1];
            float z = values[2];

            boolean pushing = false;
            if ( y > 0 ) {
                pushing = true;
            }
            updateAccelParameters(x, y, z);

            if ((!pushInitiated) && isPullOrPushed() && pushing) {
                pushInitiated = true;
            } else if ((pushInitiated) && isPullOrPushed() && pushing) {
                executePushAction(magnitude);
            } else if ((pushInitiated) && (!isPullOrPushed())) {
                pushInitiated = false;
            }
        }
    }

    private boolean isPullOrPushed() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float xWeight = 0.5f;

        magnitude = (xWeight * deltaX + deltaY);

        return (magnitude > pushThreshold);
    }

    /* Store the acceleration values given by the sensor */
    private void updateAccelParameters(float xNewAccel, float yNewAccel,
                                       float zNewAccel) {
        /* we have to suppress the first change of acceleration, it results from first values being initialized with 0 */
        if (firstUpdate) {
            xPrev = xNewAccel;
            yPrev = yNewAccel;
            zPrev = zNewAccel;
            firstUpdate = false;
        } else {
            xPrev = xAccel;
            yPrev = yAccel;
            zPrev = zAccel;
        }
        xAccel = xNewAccel;
        yAccel = yNewAccel;
        zAccel = zNewAccel;
    }

    private void executePushAction(float magnitude) {
        Toast.makeText(context, "Device was pushed: " + magnitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
