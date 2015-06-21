package com.hariharan.accelerometerdatalogger;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements SensorEventListener {
    String strDate;
    EditText nameField,freqField,timeField;
    ToggleButton toggleButton;
    Button button;
    int frequency=100;
    int time=30;
    String fileName;
    private SensorManager sensorManager;
    private Sensor aMeter,laMeter,currentSensor;
    public Acceleration acceleration;
    public int counter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameField = (EditText) findViewById(R.id.file_name);
        freqField = (EditText) findViewById(R.id.frequency);
        timeField = (EditText) findViewById(R.id.time);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        button = (Button) findViewById(R.id.button);

        acceleration =new Acceleration();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        aMeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        laMeter = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        currentSensor=aMeter;


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, currentSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        button.setEnabled(false);
        toggleButton.setEnabled(false);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        strDate= sdf.format(c.getTime());
        Toast.makeText(this,"Recording",Toast.LENGTH_SHORT).show();
        if(toggleButton.isChecked())
        {
            sensorManager.unregisterListener(this);
            currentSensor=laMeter;
            sensorManager.registerListener(this,currentSensor,SensorManager.SENSOR_DELAY_FASTEST);

        }
        else
        {
            sensorManager.unregisterListener(this);
            currentSensor=aMeter;
            sensorManager.registerListener(this,currentSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        if(nameField.getText().toString().matches(""))
            fileName=strDate;
        else
            fileName=nameField.getText()+strDate;
        if (!freqField.getText().toString().matches(""))
        {
            frequency=Integer.parseInt(freqField.getText().toString());
        }
        if(!timeField.getText().toString().matches(""))
        {
            time=Integer.parseInt(timeField.getText().toString());
        }
        ReadValues task = new ReadValues(frequency,time,this);
        task.execute();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        acceleration =new Acceleration();
        acceleration.setAcc(sensorEvent.values[0],sensorEvent.values[1],sensorEvent.values[2]);
        counter++;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
