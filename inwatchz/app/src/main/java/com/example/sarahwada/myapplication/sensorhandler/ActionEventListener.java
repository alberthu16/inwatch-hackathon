package com.example.sarahwada.myapplication.sensorhandler;

import android.hardware.SensorEventListener;

/**
 * Abstract base class for all event listeners.
 */
public abstract class ActionEventListener implements SensorEventListener {

    abstract boolean startListener(int duration);

    abstract void stopListener();

}
