package com.example.sarahwada.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        SensorHandler handler = new SensorHandler(context);


        TextView test = (TextView) findViewById(R.id.test);
    }

}
