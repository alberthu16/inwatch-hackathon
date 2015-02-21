package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.sarahwada.myapplication.models.MotionsContainer.UserAction;

import java.util.Hashtable;

;

/**
 * SensorHandler is responsible for handling listeners and sensors based on the user action.
 */
public class SensorHandler {
    // Handles listener register and unregister
    SensorManager sensorManager;

    // Event listeners for each different action
    ActionEventListener shuffleEventListener;//TODO: example, delete instances of this
    ActionEventListener pullEventListener;
    ActionEventListener pushEventListener;
    ActionEventListener twistEventListener;

    // Maps the user action to what event listeners it requires
    Hashtable<UserAction, ActionEventListener> actionListeners = new Hashtable<>();

    /**
     * Constructor for the SensorHandler
     * @param context Context app was started in (from MainActivity)
     */
    public SensorHandler(Context context) {
        // Create sensor manager
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Create all event listeners
        this.shuffleEventListener = new ShuffleEventListener(sensorManager, context);
        this.pullEventListener = new PullEventListener(sensorManager, context);
        this.pushEventListener = new PushEventListener(sensorManager, context);
        this.twistEventListener = new TwistEventListener(sensorManager, context);

        // Store event listeners
        this.actionListeners.put(UserAction.PULL, pullEventListener);
        this.actionListeners.put(UserAction.PUSH, pushEventListener);
        this.actionListeners.put(UserAction.TWIST, twistEventListener);

        Log.i("SensorHandler", "Initialized all event listeners, sensors, and structures");
        //Log.i("SensorManager", sensorManager.getSensorList(Sensor.TYPE_ALL).toString());
        //Toast.makeText(context, "Testing begins!", Toast.LENGTH_SHORT).show();

        // TODO: here for now to get something running
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(shuffleEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.unregisterListener();
    }

    public boolean handle(UserAction action, double timeout) {
        //TODO: need some way to get the timeout into the EventListener.  See note at line40

        // Get event listener associated with this command
        ActionEventListener listener = actionListeners.get(action);
        boolean success = listener.startListener(timeout);
        listener.stopListener();
        return success;
    }

}
