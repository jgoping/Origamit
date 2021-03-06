package com.example.justin.testingvisualizer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.example.justin.testingvisualizer.R.id.webview;
import static java.lang.Math.min;


public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FloatingActionButton button = (FloatingActionButton) findViewById(R.id.snap);
        Button b1 = (Button) findViewById(R.id.b1);
        Button b2 = (Button) findViewById(R.id.b2);
        Button b3 = (Button) findViewById(R.id.b3);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://origami.me/?s=";
                Button b1 = (Button) findViewById(R.id.b1);
                String s = b1.getText().toString();
                String[] arr = s.split(" ");
                int j = 0;
                for ( String ss : arr) {
                    if (j > 0) {
                        url += '+';
                    }
                    url += ss;
                    System.out.println(ss);
                    j++;
                }

                Intent menuIntent = new Intent(MainActivity.this, WebActivity.class);
                menuIntent.putExtra("url", url);
                startActivity(menuIntent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://origami.me/?s=";
                Button b2 = (Button) findViewById(R.id.b2);
                String s = b2.getText().toString();
                String[] arr = s.split(" ");
                int j = 0;
                for ( String ss : arr) {
                    if (j > 0) {
                        url += '+';
                    }
                    url += ss;
                    System.out.println(ss);
                    j++;
                }

                Intent menuIntent = new Intent(MainActivity.this, WebActivity.class);
                menuIntent.putExtra("url", url);
                startActivity(menuIntent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String url = "https://origami.me/?s=";
                Button b3 = (Button) findViewById(R.id.b3);
                String s = b3.getText().toString();
                String[] arr = s.split(" ");
                int j = 0;
                for ( String ss : arr) {
                    if (j > 0) {
                        url += '+';
                    }
                    url += ss;
                    System.out.println(ss);
                    j++;
                }

                Intent menuIntent = new Intent(MainActivity.this, WebActivity.class);
                menuIntent.putExtra("url", url);
                startActivity(menuIntent);
            }
        });
        final FloatingActionButton button2 = (FloatingActionButton) findViewById(R.id.gallery);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onImageGalleryClicked(v);
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //image bitmap is the image
            ImageView imageView = (ImageView) findViewById(R.id.result);
            imageView.setImageBitmap(imageBitmap);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";


            MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, imageFileName, "Origamit");

            FutureTask<ArrayList<String>> futureTask = new FutureTask<ArrayList<String>>(new runnable(imageFileName));
            Thread t = new Thread(futureTask);
            t.start();
            try {
                ArrayList<String> names = futureTask.get();
                Button b1 = (Button) findViewById(R.id.b1);
                b1.setText(names.get(min(0, names.size()-1)));
                Button b2 = (Button) findViewById(R.id.b2);
                b2.setText(names.get(min(1, names.size()-1)));
                Button b3 = (Button) findViewById(R.id.b3);
                b3.setText(names.get(2));
                TextView Title = (TextView) findViewById(R.id.Title);


                b1.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                Title.setVisibility(View.GONE);

                b3.setText(names.get(min(2, names.size()-1)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
        if(requestCode == IMAGE_GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            File imageFile = new File(getRealPathFromURI(imageUri));
            InputStream inputStream;
            ImageView imageView = (ImageView) findViewById(R.id.result);
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(image);

                FutureTask<ArrayList<String>> futureTask = new FutureTask<ArrayList<String>>(new runnable(imageFile));
                Thread t = new Thread(futureTask);
                t.start();
                try {
                    ArrayList<String> names = futureTask.get();
                    Button b1 = (Button) findViewById(R.id.b1);
                    b1.setText(names.get(0));
                    Button b2 = (Button) findViewById(R.id.b2);
                    b2.setText(names.get(1));
                    Button b3 = (Button) findViewById(R.id.b3);
                    b3.setText(names.get(2));
                    TextView Title = (TextView) findViewById(R.id.Title);

                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.VISIBLE);
                    Title.setVisibility(View.GONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }catch (FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onImageGalleryClicked(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
