package com.example.lukasztrojok.hour6app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class ShowActivity extends AppCompatActivity {
    ImageView camPreview;

    TextView displayFloor;
    TextView displayPlace;
    TextView displaySector;

    TextToSpeech tts;

    File sdcard = Environment.getExternalStorageDirectory();

    File file = new File(sdcard, "findCar/findMyCar.txt");


    String wholeText;
    String[] wholeTextArray;

    StringBuilder text = new StringBuilder();

    ImageButton readButton;

    String formatedRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        camPreview = (ImageView) findViewById(R.id.camPreview);

        displayFloor = (TextView) findViewById(R.id.displayFloor);
        displayPlace = (TextView) findViewById(R.id.displayPlace);
        displaySector = (TextView) findViewById(R.id.displaySector);

        readButton = (ImageButton) findViewById(R.id.readButton);


        //    String textPath = "sdcard/findCar/findMyCar.txt";
        //   displayFloor.setText(textPath);


        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');


            }
            br.close();


        } catch (IOException e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();   //make toast
        }


        wholeText = text.toString();


        wholeTextArray = wholeText.split("/");


        displayFloor.setText(wholeTextArray[0]);
        displayPlace.setText(wholeTextArray[1]);
        displaySector.setText(wholeTextArray[2]);

        String path = "sdcard/findCar/car_image.jpg";
        camPreview.setImageDrawable(Drawable.createFromPath(path));

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {


                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });


        formatedRead = "Floor number: " + wholeTextArray[0] + ". Place number: " + wholeTextArray[1] + ". Sector letter: " + wholeTextArray[2];


        readButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {


                                              String toSpeak = formatedRead.toString();
                                              tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


                                          }


                                      }


        );


        Button backButton = (Button) findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(startIntent);
            }

        });
    }


    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

}
