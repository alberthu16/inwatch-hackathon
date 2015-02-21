package com.example.sarahwada.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

/**
 *
 */
public class SensorHandler {
    SensorManager sensorManager;
    SensorEventListener shuffleEventListener;
    // TODO: add all event listeners for different "actions"

    // TODO: hashtable of ACTION -> listener

    public SensorHandler(Context context) {
        // Create sensor manager
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // TODO: create all event listeners here
        // TODO: create event listener classes
        this.shuffleEventListener = new DropEventListener(sensorManager, context);



        //Log.i("SensorManager", sensorManager.getSensorList(Sensor.TYPE_ALL).toString());
        Toast.makeText(context, "Testing begins!", Toast.LENGTH_SHORT).show();

        Sensor accelerometerSensor = sensorManager.getDefaultSensor(1);
        SensorEventListener dropEventListener = new DropEventListener(sensorManager, context);

        Log.i("SensorManager", "Created sensor and listener");

        sensorManager.registerListener(dropEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.unregisterListener();


    }

    public boolean handle(String action, int timeout) {
        //TODO
        // Find listeners for command
        // *** Note: timeout here
        // Start all listeners: registerListener
        // Wait for listerns to return: unregisterListener
        // Return success (true) or fail (false)
        return true;
    }

}
