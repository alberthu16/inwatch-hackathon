package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

import com.example.sarahwada.myapplication.models.MotionsContainer.UserAction;

import java.util.Hashtable;

/**
 * SensorHandler is responsible for handling listeners and sensors based on the user action.
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
     * @param context Context app was started in (from MainActivity)
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

        Log.i("SensorHandler", "Initialized all event listeners, sensors, and structures");

        // TODO: here for now to get something running
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        sensorManager.registerListener(pullEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(pushEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(punchEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

        handle(UserAction.TWIST, 10000);//10 seconds timeout

    }

    /**
     *
     * @param action
     * @param timeout In milliseconds
     * @return
     */
    public boolean handle(UserAction action, long timeout) {
        //TODO: need some way to get the timeout into the EventListener.  See note at line40

        // Get event listener associated with this command
        final ActionEventListener listener = actionListeners.get(action);
        listener.startListener();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.stopListener();
            }
        }, timeout);

        return listener.success;
    }

}
