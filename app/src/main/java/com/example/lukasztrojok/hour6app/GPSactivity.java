package com.example.lukasztrojok.hour6app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class GPSactivity extends AppCompatActivity {


    private Button gButton;
    private Button btnMap;
    private TextView gView;
    private Float accuracy;
    private LocationManager locationManager;
    private LocationListener locationListener;
    protected Double currentLatitude = -1.0, currentLongitude = -1.0;
    private TextView latitudeview;
    private TextView longitudeview;

    private TextView accuracytxt;

    String latitudetext;
    String longitudetext;
    String both;


    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);


        gButton = findViewById(R.id.gButton);

        gView = findViewById(R.id.gView);

        btnMap = findViewById(R.id.btn_map);


        latitudeview = (TextView) findViewById(R.id.latitude_field);
        longitudeview = (TextView) findViewById(R.id.longitude_field);

        accuracytxt = (TextView) findViewById(R.id.fieldAcc);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                accuracy = location.getAccuracy();
                accuracytxt.setText(accuracy.toString() + "meters");


                if (accuracy < 10.0 && accuracy > 0.0) {


                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();
                    //  gView.append("\n "+location.getLatitude()+" "+location.getLongitude());
                    latitudetext = new Double(currentLatitude).toString();
                    longitudetext = new Double(currentLongitude).toString();
                    latitudeview.setText(latitudetext);
                    longitudeview.setText(longitudetext);
                    locationManager.removeUpdates(locationListener);
                } else {


                    Toast.makeText(GPSactivity.this, "GPS Signal too low!", Toast.LENGTH_SHORT).show();


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
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET


            }, 10);
            return;
        } else {

            //configureButton();
            locationManager.requestLocationUpdates("gps", 0, 0, locationListener);


            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent requestLink = new Intent(GPSactivity.this, MapsActivity.class);
                    requestLink.putExtra("Lati", currentLatitude);
                    requestLink.putExtra("Longi", currentLongitude);
                    startActivity(requestLink);


                }
            });


            gButton.setOnClickListener(new View.OnClickListener() {


                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {


                    locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                    // locationManager.requestSingleUpdate(Criteria.ACCURACY_HIGH,locationListener,);


                }


            });


        }
    }
}

/*
    private void configureButton() {





        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent requestLink = new Intent(GPSactivity.this, MapsActivity.class);
                requestLink.putExtra("Lati", currentLatitude);
                requestLink.putExtra("Longi", currentLongitude);
                startActivity(requestLink);


            }
        });

    }
    */













/*
    private Button gButton;
    private Button btnMap;
    private TextView gView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    protected Double currentLatitude, currentLongitude;
    private TextView latitudeview;
    private TextView longitudeview;

    String latitudetext;
    String longitudetext;
    String both;


    @RequiresApi(api = Build.VERSION_CODES.M)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsactivity);


        gButton = findViewById(R.id.gButton);

        gView = findViewById(R.id.gView);

        btnMap = findViewById(R.id.btn_map);

        latitudeview = (TextView) findViewById(R.id.latitude_field);
        longitudeview = (TextView) findViewById(R.id.longitude_field);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                currentLatitude = location.getLatitude();


                currentLongitude = location.getLongitude();


                //  gView.append("\n "+location.getLatitude()+" "+location.getLongitude());


                latitudetext = new Double(currentLatitude).toString();
                longitudetext = new Double(currentLongitude).toString();


                latitudeview.setText(latitudetext);
                longitudeview.setText(longitudetext);


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET


            }, 10);
            return;
        } else {

            configureButton();

        }
    }


    private void configureButton() {


        gButton.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {


               // locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
locationManager.requestSingleUpdate(Criteria.ACCURACY_HIGH,locationListener,);
/*
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        locationManager.removeUpdates(locationListener);


                    }
                }, 3000);

            }


        });


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent requestLink = new Intent(GPSactivity.this, MapsActivity.class);
                requestLink.putExtra("Lati", currentLatitude);
                requestLink.putExtra("Longi", currentLongitude);
                startActivity(requestLink);


            }
        });


    }

        */





