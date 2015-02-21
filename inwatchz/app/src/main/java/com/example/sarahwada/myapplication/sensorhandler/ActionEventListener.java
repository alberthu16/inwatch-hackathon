package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Abstract base class for all event listeners.
 */
public abstract class ActionEventListener implements SensorEventListener {
    public Context context;
    public SensorManager sensorManager;
    // TODO: one for now, can be a list later on
    public Sensor sensor;

    public ActionEventListener(SensorManager sensorManager, Context context) {
        this.context = context;
        this.sensorManager = sensorManager;
    }

    public boolean startListener(double duration) {
        sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return true;
    }

    public void stopListener() {
        sensorManager.unregisterListener(this, this.sensor);
    }
}
