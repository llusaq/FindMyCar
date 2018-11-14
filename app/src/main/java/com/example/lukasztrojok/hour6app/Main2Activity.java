package com.example.lukasztrojok.hour6app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button saveButton = (Button) findViewById(R.id.btnSaveAction);
        Button showButton = (Button) findViewById(R.id.btnShowAction);
        Button saveGpsButton = (Button) findViewById(R.id.btnSaveGpsAction);
Button mapps = (Button) findViewById(R.id.btn_mapps);


        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }

        });

        showButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), ShowActivity.class);
                startActivity(startIntent);
            }

        });

        saveGpsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), GPSactivity.class);
                startActivity(startIntent);
            }

        });



        mapps.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(startIntent);
            }

        });

    }






}
