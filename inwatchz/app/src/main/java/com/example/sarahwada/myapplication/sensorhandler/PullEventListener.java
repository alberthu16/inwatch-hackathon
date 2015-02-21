package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class PullEventListener extends ActionEventListener {


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

    /* Pull Threshold */
    final float pullThreshold = 3.0f;

    /* Has a motion started */
    boolean pullInitiated = false;

    public PullEventListener(SensorManager sensorManager, Context context) {
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

            boolean pulling = false;
            if ( y < 0 ) {
                pulling = true;
            }
            updateAccelParameters(x, y, z);

            if ((!pullInitiated) && isPullOrPushed() && pulling) {
                pullInitiated = true;
            } else if ((pullInitiated) && isPullOrPushed() && pulling) {
                executePullAction(yAccel);
            } else if ((pullInitiated) && (!isPullOrPushed())) {
                pullInitiated = false;
            }
        }
    }

    /* If the values of acceleration have changed on at least two axises, we are probably in a shake motion */
    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPrev- xAccel);
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaZ = Math.abs(zPrev - zAccel);
        return (deltaX > pullThreshold && deltaY > pullThreshold)
                || (deltaX > pullThreshold && deltaZ > pullThreshold)
                || (deltaY > pullThreshold && deltaZ > pullThreshold);
    }

    private boolean isPullOrPushed() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float xWeight = 0.3f;

        return (xWeight * deltaX + deltaY > pullThreshold);
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

    private void executePullAction(float magnitude) {
        Toast.makeText(context, "Device was pulled: " + magnitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
