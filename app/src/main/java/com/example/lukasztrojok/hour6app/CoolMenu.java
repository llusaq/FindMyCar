package com.example.lukasztrojok.hour6app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CoolMenu extends AppCompatActivity {

    private ImageButton removeButton;
    private Button bigSaveBtn;
    private Button bigShowBtn;
    private Float accuracy;
    private TextView accuracytxt;
    private TextView dataAccur;
    private TextView dataLongi;
    private TextView dataLati;
    private TextView dataStatus;
    private TextView casee;
    private ImageView lights;
    private ImageButton refresh;
    private String wtext;
    private String wholeText;
    private Integer option = 0;
    String[] wholeTextArray;

    StringBuilder text = new StringBuilder();

    File sdcard = Environment.getExternalStorageDirectory();

    File file = new File(sdcard, "findCar/findMyCar.txt");
    File photo = new File(sdcard, "findCar/car_image.jpg");

    private LocationManager locationManager;
    private LocationListener locationListener;
    protected Double currentLatitude = -1.0, currentLongitude = -1.0;
    private Double reducedLat, reducedLong;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)


    public void optioncheck() {
        if (file.exists()) {
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

            try {
                wholeText = text.toString();


                wholeTextArray = wholeText.split("/");


                option = Integer.parseInt(wholeTextArray[0]);


            } catch (Exception e) {

                //   Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();   //make toast


            }
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_menu);


        accuracy = (float) 100.0;

        // option =0;


        removeButton = (ImageButton) findViewById(R.id.removebtn);


        bigSaveBtn = (Button) findViewById(R.id.bigSaveBtn);

        bigShowBtn = (Button) findViewById(R.id.bigShowBtn);
        dataStatus = (TextView) findViewById(R.id.datastatus);
        dataStatus.setText("WAITING FOR GPS");
        dataAccur = (TextView) findViewById(R.id.dataaccuracy);
        dataLati = (TextView) findViewById(R.id.datalat);
        dataLongi = (TextView) findViewById(R.id.datalong);
        refresh = (ImageButton) findViewById(R.id.refreshgps);
        lights = findViewById(R.id.light);
        // casee = (TextView) findViewById(R.id.cases);

        //  casee.setText(option.toString());


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                accuracy = location.getAccuracy();
                dataAccur.setText("ACCURACY:" + accuracy.toString() + " meters");

                if (accuracy > 0.0 && accuracy < 20.0) {
                    // option=2;
                    lights.setImageResource(R.drawable.ltgreen);
                    dataStatus.setText("GPS READY");
                    currentLongitude = location.getLongitude();
                    currentLatitude = location.getLatitude();


                    String latShort = String.format("%.3f", currentLatitude);
                    String lonShort = String.format("%.3f", currentLongitude);


                    dataLati.setText(latShort);
                    dataLongi.setText(lonShort);
                    //  locationManager.removeUpdates(locationListener);

                   /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        startcheck();
                    }
                    casee.setText(option.toString());
*/

                    bigSaveBtn.setOnClickListener(new View.OnClickListener() {


                                                      @SuppressLint("MissingPermission")
                                                      public void onClick(View v) {



/*
                                                          PrintWriter writer = null;
                                                          try {
                                                              writer = new PrintWriter("sdcard/findCar/findmycar.txt");
                                                          } catch (FileNotFoundException e) {
                                                              e.printStackTrace();
                                                          }
                                                          writer.print("");
                                                          writer.close();

*/


                                                          locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                                                          if (currentLatitude == -1.0 || currentLongitude == -1.0) {


                                                              Toast.makeText(CoolMenu.this, "TRY AGAIN", Toast.LENGTH_SHORT).show();

                                                          } else {

                                                              //option=2;

                                                              wtext = 2 + "/" + currentLongitude + "/" + currentLatitude + "/";

                                                              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                                  if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                                                                      String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                                                                      requestPermissions(permissions, WRITE_EXTERNAL_STORAGE_CODE);
                                                                  } else {
                                                                      // option=2;


                                                                      saveToTextFile(wtext);
                                                                  }

                                                              } else {
                                                                  //  option=2;
                                                                  saveToTextFile(wtext);
                                                              }


                                                          }


                                                      }


                                                  }


                    );


                } else {

                    lights.setImageResource(R.drawable.ltred);
                    dataStatus.setText("LOW GPS SIGNAL");
                    //   locationManager.removeUpdates(locationListener);

                    bigSaveBtn.setOnClickListener(new View.OnClickListener() {


                                                      public void onClick(View v) {
                                                          if (photo.exists()) {
                                                              photo.delete();

                                                          }


                                                          PrintWriter writer = null;
                                                          try {
                                                              writer = new PrintWriter("sdcard/findCar/findmycar.txt");
                                                          } catch (FileNotFoundException e) {
                                                              e.printStackTrace();
                                                          }
                                                          writer.print("");
                                                          writer.close();


                                                          locationManager.removeUpdates(locationListener);

                                                          //    option=1;
                                                          Intent intent = new Intent(CoolMenu.this, MainActivity.class);
                                                          startActivity(intent);


                                                      }


                                                  }
                    );


                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET


                }, 10);
            }

            return;
        } else {

            locationManager.requestLocationUpdates("gps", 0, 0, locationListener);


            /*
            bigShowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationManager.removeUpdates(locationListener);

                    Intent requestLink = new Intent(CoolMenu.this, MapsActivity.class);
                    requestLink.putExtra("Lati", currentLatitude);
                    requestLink.putExtra("Longi", currentLongitude);
                    startActivity(requestLink);

                }
            });
*/

        }

        refresh.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")

            @Override
            public void onClick(View v) {


                locationManager.requestLocationUpdates("gps", 0, 0, locationListener);


            }
        });


        bigSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (photo.exists()) {
                    photo.delete();

                }

                bigSaveBtn.setBackgroundResource(R.drawable.pressed);
                locationManager.removeUpdates(locationListener);
                //  option =1;


                Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(startIntent);
            }

        });


        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.exists()) {


                    option = 0;
                    file.delete();
                    Toast.makeText(CoolMenu.this, "DATA FILE REMOVED", Toast.LENGTH_SHORT).show();
                    casee.setText(option.toString());


                } else {
                    Toast.makeText(CoolMenu.this, "DATA FILE WAS ALREADY REMOVED", Toast.LENGTH_SHORT).show();

                }

                if (photo.exists()) {
                    photo.delete();

                }


            }
        });


        bigShowBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {


                bigShowBtn.setBackgroundResource(R.drawable.pressed);

                optioncheck();


                switch (option) {

                    case 1:
                        Intent startIntent = new Intent(getApplicationContext(), ShowActivity.class);
                        startActivity(startIntent);

                        break;

                    case 2:


                        Intent startIntent2 = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(startIntent2);
                        break;

                    default:

                        Toast.makeText(CoolMenu.this, "SAVE YOUR LOCATION FIRST!", Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        });
    }



/*
                Intent startIntent = new Intent(getApplicationContext(), ShowActivity.class);
                startActivity(startIntent);

                */


    @Override
    public void onBackPressed() {
        showAlertDialog();


    }

    private void showAlertDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to leave?");


        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();


    }


    private void saveToTextFile(String wholeText) {


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


            Toast.makeText(this, "LOCATION SAVED", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onResume() {


        super.onResume();
    }

    public void onPause() {

        if (locationListener != null) {

            locationManager.removeUpdates(locationListener);

        }
        Runtime.getRuntime().gc();

        super.onPause();


    }

    public void onDestroy() {
        if (locationListener != null) {

            locationManager.removeUpdates(locationListener);

        }

        Runtime.getRuntime().gc();

        super.onDestroy();


    }


}
