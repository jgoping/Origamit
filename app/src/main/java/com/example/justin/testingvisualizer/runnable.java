package com.example.justin.testingvisualizer;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.json.*;

/**
 * Created by Justin on 2017-09-16.
 */

public class runnable extends AppCompatActivity implements Runnable {
    private ArrayList<String> names;
    Thread runner;
    File chosenFileName;
    String fName;

    public runnable (String imageFileName) {
        fName = imageFileName;
        names.clear();
    }

    public runnable (File file) {
        chosenFileName = file;
        names.clear();
    }

    public void run() {
        try  {

            VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
            service.setApiKey("99e49c0dc9f6aed1839753a66f3b9d7865537e4d");


            System.out.println("Classify an image");
            ClassifyImagesOptions options;
            if (fName != null) {
                options = new ClassifyImagesOptions.Builder()
                        .images(new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES) + "/" + fName + ".jpg")) // "/storage/0123-4567/Frog.jpg"
                        .build(); //+ "/" + fName + ".jpg"
            }else{
                options = new ClassifyImagesOptions.Builder()
                        .images(chosenFileName) // "/storage/0123-4567/Frog.jpg"
                        .build(); //+ "/" + fName + ".jpg"
            }


            VisualClassification result = service.classify(options).execute();
            //System.out.println(result);


            String s = result.toString();
            //System.out.println(s);

            JSONObject obj = new JSONObject(s);
            JSONArray arr = obj.getJSONArray("images");
            obj = new JSONObject(arr.getString(0));
            arr = obj.getJSONArray("classifiers");
            obj = new JSONObject(arr.getString(0));
            arr = obj.getJSONArray("classes");
            obj = new JSONObject(arr.getString(0));

            names.add(obj.getString("class"));
            obj = new JSONObject(arr.getString(1));
            names.add(obj.getString("class"));
            obj = new JSONObject(arr.getString(2));
            names.add(obj.getString("class"));

            System.out.println(names);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getNames() {
        return names;
    }


    public void stop() {
        runner = null;
    }
}
