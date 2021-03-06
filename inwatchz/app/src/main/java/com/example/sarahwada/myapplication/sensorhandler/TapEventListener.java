package com.example.sarahwada.myapplication.sensorhandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sarahwada.myapplication.R;

/**
 *
 */
public class TapEventListener extends ActionEventListener {

    float mLastAccelWithGrav = 0.00f;
    float mAccelWithGrav = SensorManager.GRAVITY_EARTH;
    float mAccelNoGrav = SensorManager.GRAVITY_EARTH;

    /* Pull Threshold */
    final float tapThreshold = 9.0f;

    public TapEventListener(SensorManager sensorManager, Context context) {
        super(sensorManager, context);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if ((event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) && !this.success) {

            float[] values = event.values;

            /* Detected movement */
            float x = values[0];
            float y = values[1];
            float z = values[2];

            boolean tapping = (z < 0);

            mLastAccelWithGrav = mAccelWithGrav;
            mAccelWithGrav = android.util.FloatMath.sqrt(0.15f*x*x + 0.15f*y*y + 1.2f*z*z);
            float delta = mAccelWithGrav - mLastAccelWithGrav;
            mAccelNoGrav = mAccelNoGrav * 0.9f + delta; //low-pass filter


            if (mAccelNoGrav > tapThreshold) {
                executeTapAction(mAccelNoGrav);
            }
        }
    }

    private void executeTapAction(float magnitude) {
        this.success = true;
        this.context.setIsMotionCorrect(true);
        ImageView image = (ImageView) this.context.findViewById(R.id.image);
        image.setImageResource(R.drawable.tap_complete);
        MediaPlayer.create(this.context, R.raw.tap_effect).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }
}
