package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import com.example.sarahwada.myapplication.MainActivity.UserAction;

import java.util.Hashtable;

/**
 * SensorHandler is responsible for handling listeners and sensors based on the user action.
 */
// extends listener
public class SensorHandler {
    // Handles listener register and unregister
    SensorManager sensorManager;

    // Event listeners for each different action
    SensorEventListener shuffleEventListener;//TODO: example, delete instances of this
    SensorEventListener pullEventListener;
    SensorEventListener pushEventListener;
    SensorEventListener twistEventListener;

    // Maps the user action to what event listeners it requires
    Hashtable<UserAction, SensorEventListener> actionListeners = new Hashtable<>();

    /**
     * Constructor for the SensorHandler
     * @param context Context app was started in (from MainActivity)
     */
    public SensorHandler(Context context) {
        // Create sensor manager
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Create all event listeners
        // TODO: this might not work, may need to port over all listeners to this file.
        // -- Don't want multiple copies of sensorManager.
        // -- Make SensorHandler extend listener, and have multiple listener options.
        this.shuffleEventListener = new ShuffleEventListener(sensorManager, context);
        this.pullEventListener = new PullEventListener(sensorManager, context);
        this.pushEventListener = new PushEventListener(sensorManager, context);
        this.twistEventListener = new TwistEventListener(sensorManager, context);

        // Store event listeners
        this.actionListeners.put(UserAction.PULL, pullEventListener);
        this.actionListeners.put(UserAction.PUSH, pushEventListener);
        this.actionListeners.put(UserAction.TWIST, twistEventListener);

        // Create sensors
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        Log.i("SensorHandler", "Initialized all event listeners, sensors, and structures");
        //Log.i("SensorManager", sensorManager.getSensorList(Sensor.TYPE_ALL).toString());
        //Toast.makeText(context, "Testing begins!", Toast.LENGTH_SHORT).show();

        // TODO: here for now to get something running
        sensorManager.registerListener(shuffleEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.unregisterListener();
    }

    public boolean handle(UserAction action, int timeout) {
        //TODO: need some way to get the timeout into the EventListener.  See note at line40

        // Get event listener associated with this command
        SensorEventListener listener = actionListeners.get(action);
        // TODO: Timeout stuff
        // TODO: Start all listeners: registerListener
        // TODO: Wait for listerns to return: unregisterListener
        // Return success (true) or fail (false)
        return true;
    }

}
