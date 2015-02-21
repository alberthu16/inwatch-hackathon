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
    final float pushThreshold = 3.0f;

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
                executePushAction(yAccel);
            } else if ((pushInitiated) && (!isPullOrPushed())) {
                pushInitiated = false;
            }
        }
    }

    /* If the values of acceleration have changed on at least two axises, we are probably in a shake motion */
    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPrev- xAccel);
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaZ = Math.abs(zPrev - zAccel);
        return (deltaX > pushThreshold && deltaY > pushThreshold)
                || (deltaX > pushThreshold && deltaZ > pushThreshold)
                || (deltaY > pushThreshold && deltaZ > pushThreshold);
    }

    private boolean isPullOrPushed() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float xWeight = 0.3f;

        return (xWeight * deltaX + deltaY > pushThreshold);
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
