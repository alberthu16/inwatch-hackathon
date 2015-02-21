package com.example.sarahwada.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 *
 */
public class DropEventListener implements SensorEventListener {
    private SensorManager sensorManager;
    private Context context;

    public DropEventListener(SensorManager manager, Context context) {
        this.sensorManager = manager;
        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
//            if (actualTime - lastUpdate < 200) {
//                return;
//            }
            //lastUpdate = actualTime;
            Toast.makeText(context, "Device was shuffled: " + actualTime, Toast.LENGTH_SHORT).show();
//            if (color) {
//                view.setBackgroundColor(Color.GREEN);
//            } else {
//                view.setBackgroundColor(Color.RED);
//            }
//            color = !color;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

//    //@Override
//    protected void onResume() {
//        //super.onResume();
//        // register this class as a listener for the orientation and
//        // accelerometer sensors
//        sensorManager.registerListener(this,
//                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    //@Override
//    protected void onPause() {
//        // unregister listener
//        //super.onPause();
//        sensorManager.unregisterListener(this);
//    }


}
