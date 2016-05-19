package com.ziqisu.acc10;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.lang.Float;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Environment;
import android.util.Log;

/**
 * Created by ziqisu on 5/18/16.
 */
public class StoreData extends Thread implements SensorEventListener {
    //get private blocking queue to store data, time and activity type
    final static BlockingQueue<Data> queue = new ArrayBlockingQueue<Data>(100);
    final static BlockingQueue<Data> objects = new ArrayBlockingQueue<>(100);
    protected String activity = "";


    //create a method to add sensor data x,y,z, time and activity type into queue
    public static void enqueue(float[] value, String activity){
        try {
            Data data;
            if (objects.isEmpty()) {
                 data = new Data(System.currentTimeMillis(), value, activity);
            } else {
                data = objects.take();
                data.time = System.currentTimeMillis();
                data.values = value;
                data.activity = activity;
            }
            queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        String state;
        state = Environment.getExternalStorageState();

        // to check whether or not we have external storage
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // get the directory and create a folder named AccData
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/AccData");
            // if the folder does not exist, we create the folder
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            // create file name according to data and time
            DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
            String date = df.format(Calendar.getInstance().getTime());
            date = date + ".txt";
            File file = new File(Dir, date);
            try{
                DataOutputStream steam = new DataOutputStream(new FileOutputStream(file));
                final StringBuilder sb = new StringBuilder();
                while(MainActivity.start){
                    //use stringbuilder to create a line of data
                    sb.setLength(0);
                    Data data = queue.take();
                    sb.append(data.time);
                    sb.append(";ACC;");
                    for (int i = 0; i < data.values.length; i++) {
                        sb.append(data.values[i]);
                        if (i < data.values.length - 1) sb.append(";");
                    }
                    sb.append(";");
                    sb.append(data.activity);
                    sb.append("\n");
                    //write one line of data into file
                    steam.writeBytes(sb.toString());
                    objects.put(data);
                }
                //close file
                steam.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //if we press start, the following boolean will change to ture and start to
        //collect data,time and activity type
        StoreData.enqueue(event.values, activity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

