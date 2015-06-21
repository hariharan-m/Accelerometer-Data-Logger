package com.hariharan.accelerometerdatalogger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by hariharan on 21/6/15.
 */
public class ReadValues extends AsyncTask<Void,Void,Void> {
    int time;
    int frequency;
    Context context;
    long iTime, fTime;
    MainActivity mainActivity;
    String string;
    ArrayList<Acceleration> list = new ArrayList<Acceleration>();

    public ReadValues(int f, int t, Context context) {
        time = t;
        frequency = f;
        this.context = context;

        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }

    }

    @Override
    protected Void doInBackground(Void... voids) {
        list.clear();

        for (int i = 0; i < time * frequency; ) {
            iTime = System.currentTimeMillis();
            if ((iTime - fTime) > (long) 1000 / frequency) {
                list.add(mainActivity.acceleration);
                i++;
                fTime = System.currentTimeMillis();

            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, ""+mainActivity.counter+";" + list.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < list.size(); i++) {
            string = list.get(i).getX() + ";" + list.get(i).getY() + ";" + list.get(i).getZ();
            appendLog(string);
        }
        Toast.makeText(context, "Done Writing File", Toast.LENGTH_SHORT).show();
        mainActivity.button.setEnabled(true);
        mainActivity.toggleButton.setEnabled(true);
    }

    public void appendLog(String text) {
        String fileName = mainActivity.fileName;
        File logFile = new File("sdcard/"+fileName);
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}