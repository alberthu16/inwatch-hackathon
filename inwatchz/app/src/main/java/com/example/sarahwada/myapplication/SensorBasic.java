package com.example.sarahwada.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by alberthu on 2/20/15.
 */
public class SensorBasic implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mLight;
    private Sensor mAccelerometer;
    private long lastUpdate;

    public SensorBasic(Context context){
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // TODO

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }

    }

    private String getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;

        String toast = "Device was not shuffled";
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return "nope";
            }
            lastUpdate = actualTime;
            toast = "Device was shuffled";
            //Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
            //        .show();
//            if (color) {
//                view.setBackgroundColor(Color.GREEN);
//            } else {
//                view.setBackgroundColor(Color.RED);
//            }
//            color = !color;
        }
        return toast;
    }

    public void register(){
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        mSensorManager.unregisterListener(this);
    }
}