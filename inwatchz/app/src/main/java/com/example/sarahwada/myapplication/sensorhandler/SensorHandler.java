package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;
import com.example.sarahwada.myapplication.models.MotionsContainer.UserAction;
import java.util.Hashtable;

/**
 * SensorHandler is responsible for handling listeners and sensors based on the user action.
 * There is an event listener for each action, which has its corresponding sensor(s).
 */
public class SensorHandler {
    // Handles listener register and unregister
    SensorManager sensorManager;

    // Event listeners for each different action
    ActionEventListener pullEventListener;
    ActionEventListener pushEventListener;
    ActionEventListener punchEventListener;
    ActionEventListener twistEventListener;

    // Maps the user action to what event listeners it requires
    Hashtable<UserAction, ActionEventListener> actionListeners = new Hashtable<>();

    /**
     * Constructor for the SensorHandler
     * @param context Context app was started in, from GameActivity
     */
    public SensorHandler(Context context) {
        // Create sensor manager
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Create all event listeners
        this.pullEventListener = new PullEventListener(sensorManager, context);
        this.pushEventListener = new PushEventListener(sensorManager, context);
        this.punchEventListener = new PunchEventListener(sensorManager, context);
        this.twistEventListener = new TwistEventListener(sensorManager, context);

        // Store event listeners
        this.actionListeners.put(UserAction.PULL, pullEventListener);
        this.actionListeners.put(UserAction.PUSH, pushEventListener);
        this.actionListeners.put(UserAction.TWIST, twistEventListener);
        this.actionListeners.put(UserAction.PUNCH, punchEventListener);

        Log.i("SensorHandler", "Initialized all event listeners, sensors, and structures");
    }

    public void handle(UserAction action) {
        // Get event listener associated with this command
        final ActionEventListener listener = actionListeners.get(action);
        listener.startListener();
        Log.i("SensorHandler", "in handle method, started listener");
    }

    public void cleanup() {
        for (ActionEventListener listener : this.actionListeners.values()) {
            if (listener.isActive) {
                listener.stopListener();
            }
        }
        Log.i("SensorHandler", "successfully cleaned up all listeners");
    }

}
