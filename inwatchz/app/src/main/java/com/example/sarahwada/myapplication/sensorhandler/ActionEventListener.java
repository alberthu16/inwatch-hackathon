package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Abstract base class for all event listeners.
 */
public abstract class ActionEventListener implements SensorEventListener {
    public Context context;
    public SensorManager sensorManager;
    public boolean success = false;
    // TODO: one for now, can be a list later on
    public Sensor sensor;

    public ActionEventListener(SensorManager sensorManager, Context context) {
        this.context = context;
        this.sensorManager = sensorManager;
    }

    public void startListener(double timeout) {
        Log.i("EventListener", "start listening");
        //TODO: timeout logic
        sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListener() {
        this.success = false;
        sensorManager.unregisterListener(this, this.sensor);
        Log.i("EventListener", "stop listening");
    }
}
