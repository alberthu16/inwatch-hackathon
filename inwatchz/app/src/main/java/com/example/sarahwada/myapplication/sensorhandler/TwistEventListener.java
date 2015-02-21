package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class TwistEventListener extends ActionEventListener {
    private Context context;
    private SensorManager sensorManager;

    public TwistEventListener(SensorManager sensorManager, Context context) {
        this.context = context;
        this.sensorManager = sensorManager;    }

    public boolean startListener(int duration) {
        //sensorManager.registerListener()
        return true;
    }

    public void stopListener() {
        //sensorManager.unregisterListener()
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) {
            Toast.makeText(context, "Device was shuffled: " + actualTime, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
