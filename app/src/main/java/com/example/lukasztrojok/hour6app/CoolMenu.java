package com.example.lukasztrojok.hour6app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CoolMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_menu);


        Button bigSaveBtn = (Button) findViewById(R.id.bigSaveBtn);
        Button bigShowBtn = (Button) findViewById(R.id.bigShowBtn);

       bigSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }

        });



       //case not saved previously then toast about need to save first and dont do nothing


        //recognize signal strength and decide about which save method to choose


        bigShowBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                Intent startIntent = new Intent(getApplicationContext(), ShowActivity.class);
                startActivity(startIntent);
            }

        });









    }
}
