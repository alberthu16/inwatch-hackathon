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
    final float pullThreshold = 5.5f;
    float magnitude;

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

            boolean pulling = false;
            if ( y < 0 ) {
                pulling = true;
            }
            updateAccelParameters(x, y, z);

            if ((!pullInitiated) && isPullOrPushed() && pulling) {
                pullInitiated = true;
            } else if ((pullInitiated) && isPullOrPushed() && pulling) {
                executePullAction(magnitude);
            } else if ((pullInitiated) && (!isPullOrPushed())) {
                pullInitiated = false;
            }
        }
    }

    private boolean isPullOrPushed() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float xWeight = 0.35f;

        magnitude = (xWeight * deltaX + deltaY);

        return (magnitude > pullThreshold);
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
