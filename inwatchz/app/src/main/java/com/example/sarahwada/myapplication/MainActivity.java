package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sarahwada.myapplication.sensorhandler.SensorHandler;


public class MainActivity extends Activity {
    private Context context;

    // TODO(yujun): I put the enum here so I could use it in the SensorHandler, but feel free to move it. - Sarah
    public enum UserAction {
        PUSH, PULL, TWIST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        SensorHandler handler = new SensorHandler(context);


        TextView test = (TextView) findViewById(R.id.test);
    }

}
