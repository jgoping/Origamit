package com.example.justin.testingvisualizer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.json.*;

import static android.R.attr.src;

/**
 * Created by Justin on 2017-09-16.
 */

public class runnable extends AppCompatActivity implements Runnable {

    Thread runner;
    MainActivity mainView;

    private static final int PERMISSION_REQUEST_CODE = 1;


    public runnable(MainActivity mV) {
        mainView = mV;
    }

    public void run() {
        try  {

            VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
            service.setApiKey("99e49c0dc9f6aed1839753a66f3b9d7865537e4d");

            /*
            File folder = new File("/storage");
            String[] listOfFiles = folder.list();

            for (String path : listOfFiles) {
                System.out.println(path);
            }
            */



            System.out.println("Classify an image");
            ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                    .images(new File("/storage/0123-4567/Frog.jpg")) //"/storage/0123-4567/Dog.jpg"
                    .build();
            VisualClassification result = service.classify(options).execute();
            System.out.println(result);
            System.out.println("ifjdsifjdsof");


            String s = result.toString();
            System.out.println(s);
            System.out.println("ifjdsifjdsof2");

            JSONObject obj = new JSONObject(s);
            JSONArray arr = obj.getJSONArray("images");
            obj = new JSONObject(arr.getString(0));
            arr = obj.getJSONArray("classifiers");
            obj = new JSONObject(arr.getString(0));
            arr = obj.getJSONArray("classes");
            obj = new JSONObject(arr.getString(0));
            ArrayList<String> names = new ArrayList<String>();
            names.add(obj.getString("class"));
            obj = new JSONObject(arr.getString(1));
            names.add(obj.getString("class"));
            obj = new JSONObject(arr.getString(2));
            names.add(obj.getString("class"));
            System.out.println(names);

            /*
            for (int i = 0; i < arr.length(); i++)
                System.out.println(arr.get(i));
                */
            //System.out.println(arr.get(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void stop() {
        runner = null;
    }
}
