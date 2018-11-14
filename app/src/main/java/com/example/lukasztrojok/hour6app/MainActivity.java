package com.example.lukasztrojok.hour6app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView txvResultFloor;
    private TextView txvResultSec;
    private TextView txvResultPlace;

    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;


    String floorNumber;
    String placeNumber;
    String sectorLetter;
    String wholeText;

    ImageButton imageButton;
    ImageView camPreview;


    Integer whichButton;

    static final int CAM_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvResultFloor = (TextView) findViewById(R.id.txvResultFloor);
        txvResultSec = (TextView) findViewById((R.id.txvResultSec));
        txvResultPlace = (TextView) findViewById(R.id.txvResultPlace);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        camPreview = (ImageView) findViewById(R.id.camPreview);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, CAM_REQUEST);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View c) {

                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getFile();
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(camera_intent, CAM_REQUEST);
            }

        });


        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(startIntent);
            }

        });

        Button realSaveCancel = (Button) findViewById(R.id.btnSave);
        realSaveCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {



                floorNumber=txvResultFloor.getText().toString().trim();
                placeNumber=txvResultPlace.getText().toString().trim();
                sectorLetter=txvResultSec.getText().toString().trim();

                if(floorNumber.isEmpty()||placeNumber.isEmpty()||sectorLetter.isEmpty()){


                    Toast.makeText(MainActivity.this,"FILL IN ALL TEXT FIELDS!", Toast.LENGTH_SHORT);

                }
                else{

                    wholeText = floorNumber + "/" +placeNumber + "/" +sectorLetter;

                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){

                        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                            requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);
                        }
                        else{
                            saveToTextFile(wholeText);
                        }

                    }
                    else{

                        saveToTextFile(wholeText);
                    }



                }





                Intent startIntent = new Intent(getApplicationContext(), Main2Activity.class);
                simpleToastClick();
                startActivity(startIntent);
            }

        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {

            case WRITE_EXTERNAL_STORAGE_CODE:{


                if(grantResults.length >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    saveToTextFile(wholeText);
                }
                else{


                    Toast.makeText(this, "Storage permission required",Toast.LENGTH_SHORT).show();

                }

            }


        }




    }

    private void saveToTextFile(String wholeText)
    {


        try {
            File folder = new File("sdcard/findCar");


            if (!folder.exists()) {
                folder.mkdir();

            }
            String fileName = "findMyCar.txt";

            File file = new File(folder, fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(wholeText);


            bw.close();


            Toast.makeText(this, fileName+" "+folder, Toast.LENGTH_SHORT).show();

        }
        catch(Exception e) {

            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        }

    public void getSpeechInput(View view) {

        whichButton = view.getId();

        //view.getContext().getString(view.getId())
        //getResources().getString(v.getId())
        // v.getContext().getString(v.getId())


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your device doesnt support speech input", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);


                    switch (whichButton) {

                        case R.id.btnSpeakFloor:

                            Toast toast = Toast.makeText(this, "floor", Toast.LENGTH_SHORT);
                            toast.show();
                            txvResultFloor.setText(result.get(0));
                            break;

                        case R.id.btnSpeakPlaceNum:

                            Toast toast1 = Toast.makeText(this, "place", Toast.LENGTH_SHORT);
                            toast1.show();
                            txvResultPlace.setText(result.get(0));
                            break;

                        case R.id.btnSpeakSec:
                            Toast toast2 = Toast.makeText(this, "sector", Toast.LENGTH_SHORT);
                            toast2.show();
                            txvResultSec.setText(result.get(0));
                            break;


                    }


                }
                break;
        }

        String path = "sdcard/findCar/car_image.jpg";
        camPreview.setImageDrawable(Drawable.createFromPath(path));
    }


    private File getFile() {
        File folder = new File("sdcard/findCar");


        if (!folder.exists()) {
            folder.mkdir();

        }


        File image_file = new File(folder, "car_image.jpg");
        return image_file;

    }

    public void simpleToastClick() {

        Toast toast = Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT);
        toast.show();

    }


}