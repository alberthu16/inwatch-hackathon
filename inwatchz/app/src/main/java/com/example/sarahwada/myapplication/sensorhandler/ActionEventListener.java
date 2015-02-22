package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.sarahwada.myapplication.GameActivity;

/**
 * Abstract base class for all event listeners.
 */
public abstract class ActionEventListener implements SensorEventListener {
    public GameActivity context;
    public SensorManager sensorManager;
    public boolean success = false;
    public boolean isActive = false;
    public Sensor sensor;

    public ActionEventListener(SensorManager sensorManager, Context context) {
        this.context = (GameActivity) context;
        this.sensorManager = sensorManager;
    }

    public void startListener() {
        Log.i("EventListener", "start listening");
        this.isActive = true;
        this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListener() {
        this.isActive = false;
        this.success = false;
        this.sensorManager.unregisterListener(this, this.sensor);
        Log.i("EventListener", "stop listening");
    }
}
