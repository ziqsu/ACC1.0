package com.ziqisu.acc10;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.widget.Button;
import android.widget.TextView;



import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    //assign private sensor and sensor manager
    private Sensor mySensor;
    private SensorManager SM;

    // start and stop button will change the boolean start to be true and false
    static boolean start = false;
    //set string activityType to empty
    private String activityType = "";
    protected StoreData store = null;
    //set up activitytext, it will take show on the screen which activity is been recording now
    TextView activitytext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("main", "start");

        //create a sensor manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        //accelerometer sensor
        mySensor= SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //set activitytext and cursor to invisible
        activitytext = (TextView) findViewById(R.id.activitytext);
        activitytext.setVisibility(View.GONE);
        activitytext.setCursorVisible(false);

        //set up all the button
        setupStartButton();
        setupWalkButton();
        setupRunButton();
        setupSitButton();
        setupStopButton();
        setupClimbButton();
        setupDriveButton();
        setupNoneButton();
    }



    private void setupStartButton() {
        //get a reference to the button
        Button walkbutton = (Button)findViewById(R.id.startbutton);
        //set the click listener to run my code
        walkbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //after press start, set start to true it will start store data
                        start = true;
                        store = new StoreData();
                        store.start();
                        SM.registerListener(store, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
                        //it will show on screen that the accelerometer starts to collect data
                        activitytext.setText("Start Collecting Data");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void setupWalkButton() {
        //get a reference to the button
        Button walkbutton = (Button)findViewById(R.id.walkbutton);
        //set the click listener to run my code
        walkbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (store!= null) {
                            //change activity type and show on the screen activity type
                            store.activity ="Walk";
                            activitytext.setText("Walk");
                            activitytext.setVisibility(View.VISIBLE);
                        }
                    }
                    
                }
        );
    }
    private void setupRunButton() {
        //get a reference to the button
        Button walkbutton = (Button)findViewById(R.id.runbutton);
        //set the click listener to run my code
        walkbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //change activity type and show on the screen activity type
                        store.activity="Run";
                        activitytext.setText("Run");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }
    private void setupSitButton() {
        //get a reference to the button
        Button walkbutton = (Button)findViewById(R.id.sitbutton);
        //set the click listener to run my code
        walkbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //change activity type and show on the screen activity type
                        store.activity = "Sit";
                        activitytext.setText("Sit");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void setupStopButton() {
        //get a reference to the button
        Button walkbutton = (Button)findViewById(R.id.stopbutton);
        //set the click listener to run my code
        walkbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //stop collecting data and unregister the sensor manager
                        start = false;
                        SM.unregisterListener(store);
                        activitytext.setText("Stop Collecting data");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void setupClimbButton() {
        //get a reference to the button
        Button climbbutton = (Button)findViewById(R.id.climbbutton);
        //set the click listener to run my code
        climbbutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //change activity type and show on the screen activity type
                        store.activity="Climb Stairs";
                        activitytext.setText("Climb Stairs");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void setupDriveButton() {
        //get a reference to the button
        Button drivebutton = (Button)findViewById(R.id.drivebutton);
        //set the click listener to run my code
        drivebutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //change activity type and show on the screen activity type
                        store.activity="Drive";
                        activitytext.setText("Drive");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void setupNoneButton() {
        //get a reference to the button
        Button nonebutton = (Button)findViewById(R.id.nonebutton);
        //set the click listener to run my code
        nonebutton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //change activity type to none press this button means you cannot find 
                        //the activity you are doing on the screen
                        store.activity="";
                        activitytext.setText("No Activity");
                        activitytext.setVisibility(View.VISIBLE);
                    }
                }
        );
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}


