package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class PunchEventListener extends ActionEventListener {


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
    final float punchThreshold = 5.0f;
    float magnitude;

    /* Has a motion started */
    boolean punchInitiated = false;

    public PunchEventListener(SensorManager sensorManager, Context context) {
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
            boolean punching = false;
            if ( x < 0 ) {
                punching = true;
            }
            float y = values[1];
            float z = values[2];

            updateAccelParameters(x, y, z);

            if ((!punchInitiated) && isPunched() && punching) { // x<0 for right-handed only
                punchInitiated = true;
            } else if ((punchInitiated) && isPunched() && punching) {
                executePunchAction(magnitude);
            } else if ((punchInitiated) && (!isPunched())) {
                punchInitiated = false;
            }
        }
    }

    private boolean isPunched() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float yWeight = 0.1f;

        magnitude = (yWeight * deltaY + 1.3f * deltaX);

        return (magnitude > punchThreshold);
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

    private void executePunchAction(float magnitude) {
        Toast.makeText(context, "ow: " + magnitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
