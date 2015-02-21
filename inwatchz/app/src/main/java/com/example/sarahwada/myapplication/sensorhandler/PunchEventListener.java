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
    final float punchThreshold = 3.0f;

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

            /* Detected movement */
            float x = values[0];
            float y = values[1];
            float z = values[2];

            updateAccelParameters(x, y, z);

            if ((!punchInitiated) && isPunched()) {
                punchInitiated = true;
            } else if ((punchInitiated) && isPunched()) {
                executePullAction(yAccel);
            } else if ((punchInitiated) && (!isPunched())) {
                punchInitiated = false;
            }
        }
    }

    /* If the values of acceleration have changed on at least two axises, we are probably in a shake motion */
    private boolean isAccelerationChanged() {
        float deltaX = Math.abs(xPrev- xAccel);
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaZ = Math.abs(zPrev - zAccel);
        return (deltaX > punchThreshold && deltaY > punchThreshold)
                || (deltaX > punchThreshold && deltaZ > punchThreshold)
                || (deltaY > punchThreshold && deltaZ > punchThreshold);
    }

    private boolean isPunched() {
        float deltaY = Math.abs(yPrev - yAccel);
        float deltaX = Math.abs(xPrev - xAccel);
        float yWeight = 0.2f;

        return (yWeight * deltaY + deltaX > punchThreshold);
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
        Toast.makeText(context, "ow: " + magnitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
